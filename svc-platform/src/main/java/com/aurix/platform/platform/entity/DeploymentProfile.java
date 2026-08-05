package com.aurix.platform.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class DeploymentProfile {
    @Enumerated(EnumType.STRING)
    @Column(name = "tenancy", length = 20)
    private TenancyType tenancy;
    @Enumerated(EnumType.STRING)
    @Column(name = "cloud", length = 20)
    private CloudProvider cloud;
    @Enumerated(EnumType.STRING)
    @Column(name = "topology", length = 30)
    private TopologyType topology;


    public enum TenancyType {
        MULTI_TENANT, SELF_HOSTED;
    }


    public enum CloudProvider {
        AWS, AZURE, GCP, OPENSTACK, PRIVATE;
    }


    public enum TopologyType {
        MODULAR_MONOLITH, MICROSERVICES_SHARED_DB;
    }

    @java.lang.SuppressWarnings("all")
    public TenancyType getTenancy() {
        return this.tenancy;
    }

    @java.lang.SuppressWarnings("all")
    public CloudProvider getCloud() {
        return this.cloud;
    }

    @java.lang.SuppressWarnings("all")
    public TopologyType getTopology() {
        return this.topology;
    }

    @java.lang.SuppressWarnings("all")
    public void setTenancy(final TenancyType tenancy) {
        this.tenancy = tenancy;
    }

    @java.lang.SuppressWarnings("all")
    public void setCloud(final CloudProvider cloud) {
        this.cloud = cloud;
    }

    @java.lang.SuppressWarnings("all")
    public void setTopology(final TopologyType topology) {
        this.topology = topology;
    }

    @java.lang.SuppressWarnings("all")
    public DeploymentProfile() {
    }

    @java.lang.SuppressWarnings("all")
    public DeploymentProfile(final TenancyType tenancy, final CloudProvider cloud, final TopologyType topology) {
        this.tenancy = tenancy;
        this.cloud = cloud;
        this.topology = topology;
    }
}
