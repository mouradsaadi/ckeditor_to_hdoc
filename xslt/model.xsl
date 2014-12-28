<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
      xmlns:xs="http://www.w3.org/2001/XMLSchema"
      exclude-result-prefixes="xs"
      version="2.0">
    
    <xsl:output method="xml" indent="yes"/>
    <xsl:template match="root">
        <html xmlns="http://www.utc.fr/ics/hdoc/xhtml">
            <head>
                <title>CKeditor Text</title>
                <meta charset="utf-8"/>
                <meta name="generator" content="HdocConverter/Optim1.2"/>
                <meta name="author" content="Ckeditor"/>
            </head>
            <body>
                <xsl:copy-of select="./*"/>
            </body>
        </html>
    </xsl:template>  

</xsl:stylesheet>
