6-07-29 21:35:29.775) Debug :  File:() Line : (0) ThreadID: (8528) queryRootNode rootName =  "volume"
(2026-07-29 21:35:29.775) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:29.784) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:29.784) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:29.784) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:29.784) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:29.792) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:29.792) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:29.792) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:29.792) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:29.792) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:29.798) Debug :  File:() Line : (0) ThreadID: (8528) queryNodeChildItems end
(2026-07-29 21:35:29.798) Debug :  File:() Line : (0) ThreadID: (8528) DrLibManager::requestFilterItemInfo filterDataMap =  QMap(("deletefiles", 1516136)("existsfiles", 396328)("filesize100mb1gb", 265)("filesize1mb100mb", 26764)("filesizeless1mb", 1885412)("filesizemore1gb", 23)("timelessoneday", 894)("timeonemonth", 450309)("timeoneweek", 190616)("timeonewyear", 1130423)("timeyesterday", 102185))
(2026-07-29 21:35:29.800) Debug :  File:() Line : (0) ThreadID: (8528) queryRootNodeAllData start
(2026-07-29 21:35:29.801) Debug :  File:() Line : (0) ThreadID: (8528) queryRootNodeAllData count =  1
(2026-07-29 21:35:30.379) Debug :  File:() Line : (0) ThreadID: (56376) DeliverServerNotify kMsgScanFinish
(2026-07-29 21:35:30.388) Debug :  File:() Line : (0) ThreadID: (49676) DrLibManager::doStartScan finished code =  0
(2026-07-29 21:35:30.388) Debug :  File:() Line : (0) ThreadID: (49676) DrLibManager::doStartScan mseconds =  929004
(2026-07-29 21:35:30.445) Debug :  File:(pluginmanager.cpp) Line : (736) ThreadID: (49676) PluginManager dreFunctionCall type =  2
(2026-07-29 21:35:30.447) Debug :  File:() Line : (0) ThreadID: (49676) DrLibManager::doStartScan start  QMap(("DeepScanType", QVariant(int, 3))("DeviceId", QVariant(int, 1))("DeviceType", QVariant(int, 0))("FileStatus", QVariant(int, 0))("LostFileNameText", QVariant(QString, "lost file name"))("RecycleScan", QVariant(bool, false))("ScanLetter", QVariant(QString, "C:"))("ScanPath", QVariant(QString, ""))("ScanType", QVariant(int, 256))("SupportFileType", QVariant(QString, "JPG|TIF|PNG|BMP|GIF|PSD|CRW|CR2|NEF|ORF|RAF|SR2|MRW|DCR|WMF|DNG|ERF|RAW|AIF|M4A|MP3|WAV|WMA|MID|OGG|AAC|AVI|MOV|MP4|M4V|3GP|3G2|WMV|ASF|FLV|SWF|MPG|RM|DOC|DOCX|XLS|XLSX|PPT|PPTX|PDF|CWK|HTM|INDD|EPS|ZIP|RAR|SIT|PST|DBX|EMLX|ARW|NRW|PEF|RW2|X3F|GPR|3FR|FFF|SVG|SKETCH|SQLITE|DWF|XD|IDML|DWG|CDR|AI|INDT|DWS|DWT|DXF|M2TS|M2T|MTS|HWP|HWPX|RTF|VSD|PAGES|NUMBERS|KEY|MXF|ARI|ARX|R3D|MKV|7Z|HEIC|HIF|AVIF|CR3|PSB|RMVB|EML|SCR|GZ|PRPROJ|CAF|ICO|CUR|CHM|ANI|TTF|MBOX|NEV|ISO|DPX|ONE|FCPEVENT|SKP|MDB|ACCDB|MOF")))
(2026-07-29 21:35:30.529) Debug :  File:(scanbottomframe.cpp) Line : (296) ThreadID: (20304) showProgressValue =  30
(2026-07-29 21:35:30.532) Debug :  File:(scanbottomframe.cpp) Line : (1015) ThreadID: (20304) window handle: QWidgetWindow(0x18f46789a70, name="MainWindowClassWindow") taskbar handle: QWidgetWindow(0x18f46789a70, name="MainWindowClassWindow")
(2026-07-29 21:35:30.741) Debug :  File:(scanmanager.cpp) Line : (1070) ThreadID: (55772) updateScanDataThread rootLocationTreeItemDataList count =  4
(2026-07-29 21:35:30.742) Debug :  File:() Line : (0) ThreadID: (55772) updateRootTreeData createTreeItem finished
(2026-07-29 21:35:30.742) Debug :  File:(scanmanager.cpp) Line : (1083) ThreadID: (55772) updateScanDataThread rootTypeTreeItemDataList count =  11
(2026-07-29 21:35:30.743) Debug :  File:() Line : (0) ThreadID: (55o(6);
        assertThat(response.getBody().getAnoReferencia()).isEqualTo(2026);
    }

    @Test
    void devePagarFatura() {
        var fecharResponse = rest.postForEntity(url("/faturas/fechar?cartaoId=" + cartaoId + "&mes=6&ano=2026"),
            null, FaturaResponse.class);
        var faturaId = fecharResponse.getBody().getId();

        var pagamentoReq = new PagarFaturaRequest();
        pagamentoReq.setValorPagamento(BigDecimal.valueOf(100));

        var response = rest.postForEntity(url("/faturas/" + faturaId + "/pagar"), pagamentoReq, FaturaResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getValorPago()).isEqualByComparingTo(BigDecimal.valueOf(100));
    }

    @Test
    void deveConsultarFatura() {
        var fecharResponse = rest.postForEntity(url("/faturas/fechar?cartaoId=" + cartaoId + "&mes=6&ano=2026"),
            null, FaturaResponse.class);
        var faturaId = fecharResponse.getBody().getId();

        var response = rest.getForEntity(url("/faturas/" + faturaId), FaturaDetalhadaResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getCodigoFatura()).isNotBlank();
        assertThat(response.getBody().getLancamentos()).isNotNull();
    }

    @Test
    void deveListarFaturas() {
        rest.postForEntity(url("/faturas/fechar?cartaoId=" + cartaoId + "&mes=6&ano=2026"),
            null, FaturaResponse.class);

        var response = rest.getForEntity(url("/faturas?cartaoId=" + cartaoId), FaturaResponse[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
    }
}
