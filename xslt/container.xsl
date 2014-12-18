<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">
    <xsl:template match="root">
        <xsl:processing-instruction name="oxygen">
            RNGSchema="http://scenari.utc.fr/hdoc/schemas/xhtml/hdoc1-xhtml.rng" type="xml"
        </xsl:processing-instruction>
        <container version="1.0" xmlns="urn:utc.fr:ics:hdoc:container">
            <rootfiles>
                <rootfile full-path="content.xml" media-type="text/xml" />
            </rootfiles>
        </container>
    </xsl:template>    
</xsl:stylesheet>