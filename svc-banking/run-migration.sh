#!/bin/bash
set -e

TARGET="svc-banking/src/main/java/com/aurix/platform/banking"
TEST_TARGET="svc-banking/src/test/java/com/aurix/platform/banking"
BACKEND=$(pwd)

rm -rf $TARGET $TEST_TARGET

mkdir -p $TARGET/core/{entity,dto,repository,service,controller,config,integration,event}
mkdir -p $TARGET/poupanca/{entity,dto,repository,service,controller,config,event,client}
mkdir -p $TARGET/salario/{entity,dto,repository,service,controller,config,event,client,job}
mkdir -p $TARGET/pricing/{entity,repository,service,controller,config}
mkdir -p $TARGET/settlement/{entity,repository,service,controller,config}

mkdir -p $TEST_TARGET/core/{service,integration,resilience}
mkdir -p $TEST_TARGET/poupanca/{client,controller,integration}
mkdir -p $TEST_TARGET/salario/{client,controller,service,integration,config}
mkdir -p $TEST_TARGET/pricing/{config,integration}
mkdir -p $TEST_TARGET/settlement/{config,integration,service}

copy_and_transform() {
  local src_pkg=$1
  local src_dir=$2
  local tgt_pkg=$3
  local tgt_dir=$4
  local old_pkg="com.aurix.platform.$src_pkg"
  local new_pkg="com.aurix.platform.banking.$tgt_pkg"

  find "$src_dir" -name '*.java' | while read f; do
    local rel="${f#$src_dir/}"
    local out="$tgt_dir/$rel"
    mkdir -p "$(dirname "$out")"
    sed "s/package $old_pkg/package $new_pkg/g; s/import $old_pkg\./import $new_pkg./g; s/import $old_pkg;/import $new_pkg;/g" "$f" > "$out"
  done
}

echo "=== Copying aurix-core -> banking.core ==="
copy_and_transform "core" "aurix-core/src/main/java/com/aurix/platform/core" "core" "$TARGET/core"
copy_and_transform "core" "aurix-core/src/test/java/com/aurix/platform/core" "core" "$TEST_TARGET/core"

echo "=== Copying aurix-poupanca -> banking.poupanca ==="
copy_and_transform "poupanca" "aurix-poupanca/src/main/java/com/aurix/platform/poupanca" "poupanca" "$TARGET/poupanca"
copy_and_transform "poupanca" "aurix-poupanca/src/test/java/com/aurix/platform/poupanca" "poupanca" "$TEST_TARGET/poupanca"

echo "=== Copying aurix-salario -> banking.salario ==="
copy_and_transform "salario" "aurix-salario/src/main/java/com/aurix/platform/salario" "salario" "$TARGET/salario"
copy_and_transform "salario" "aurix-salario/src/test/java/com/aurix/platform/salario" "salario" "$TEST_TARGET/salario"

echo "=== Copying aurix-pricing -> banking.pricing ==="
copy_and_transform "pricing" "aurix-pricing/src/main/java/com/aurix/platform/pricing" "pricing" "$TARGET/pricing"
copy_and_transform "pricing" "aurix-pricing/src/test/java/com/aurix/platform/pricing" "pricing" "$TEST_TARGET/pricing"

echo "=== Copying aurix-settlement -> banking.settlement ==="
copy_and_transform "settlement" "aurix-settlement/src/main/java/com/aurix/platform/settlement" "settlement" "$TARGET/settlement"
copy_and_transform "settlement" "aurix-settlement/src/test/java/com/aurix/platform/settlement" "settlement" "$TEST_TARGET/settlement"

echo "=== Removing duplicate/skipped files ==="
rm -f "$TARGET/settlement/entity/ConciliacaoBancaria.java"
rm -f "$TARGET/settlement/entity/SaldoConta.java"
rm -f "$TARGET/settlement/entity/OutboxEvent.java"
rm -f "$TARGET/settlement/service/OutboxRelay.java"
rm -f "$TARGET/settlement/service/OutboxEventPublisher.java"
rm -f "$TARGET/settlement/repository/OutboxEventRepository.java"
rm -f "$TARGET/settlement/repository/SaldoContaRepository.java"

# Remove test files that reference deleted settlement classes
rm -f "$TEST_TARGET/settlement/service/OutboxRelayTest.java"
rm -f "$TEST_TARGET/settlement/service/OutboxEventPublisherTest.java"

echo "=== Also skip ConciliacaoBancaria from core for now ==="
# Keep ConciliacaoBancaria and related in core for now (they compile)
# The canonical version goes to compliance in F7

echo "=== Creating BankingApplication ==="
cat > svc-banking/src/main/java/com/aurix/platform/banking/BankingApplication.java << 'APPLICATION'
package com.aurix.platform.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {
    "com.aurix.platform.banking",
    "com.aurix.platform.shared"
})
@EntityScan(basePackages = {
    "com.aurix.platform.banking.core.entity",
    "com.aurix.platform.banking.poupanca.entity",
    "com.aurix.platform.banking.salario.entity",
    "com.aurix.platform.banking.pricing.entity",
    "com.aurix.platform.banking.settlement.entity",
    "com.aurix.platform.shared.entity",
    "com.aurix.platform.organization.entity"
})
@EnableJpaRepositories(basePackages = {
    "com.aurix.platform.banking.core.repository",
    "com.aurix.platform.banking.poupanca.repository",
    "com.aurix.platform.banking.salario.repository",
    "com.aurix.platform.banking.pricing.repository",
    "com.aurix.platform.banking.settlement.repository"
})
@EnableKafka
@EnableScheduling
@EnableCaching
public class BankingApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, args);
    }
}
APPLICATION

echo "=== Creating SecurityConfig ==="
cat > svc-banking/src/main/java/com/aurix/platform/banking/config/SecurityConfig.java << 'SECURITY'
package com.aurix.platform.banking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
            );
        return http.build();
    }
}
SECURITY

echo "=== Counting files ==="
echo "Main sources:"
find svc-banking/src/main/java -name '*.java' | wc -l
echo "Test sources:"
find svc-banking/src/test/java -name '*.java' | wc -l

echo "=== Migration complete ==="
