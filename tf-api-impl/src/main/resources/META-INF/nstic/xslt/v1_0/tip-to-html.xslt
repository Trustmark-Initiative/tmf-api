<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
                xmlns:tf="https://trustmark.gtri.gatech.edu/specifications/trustmark-framework/1.0/schema/"
                xmlns="http://www.w3.org/1999/xhtml"
                xmlns:xhtml="http://www.w3.org/1999/xhtml"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


    <!-- This should work, but because the DTD takes too long to download I am excluding it -->
    <!--<xsl:output method="xml" indent="yes"-->
                <!--doctype-public="-//W3C//DTD XHTML 1.1//EN"-->
                <!--doctype-system="http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd" />-->

    <xsl:output method="xml" indent="yes" />


    <xsl:template match="tf:TrustInteroperabilityProfile">
        <html>
            <head>
                <title><xsl:value-of select="string(./tf:Name)" /><xsl:text> | GTRI NSTIC Trustmark Pilot</xsl:text></title>

                <!--&lt;!&ndash; See http://www.bootstrapcdn.com/ &ndash;&gt;-->
                <!--<xhtml:link href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" />-->
                <!--<xhtml:script src="https://code.jquery.com/jquery-2.1.1.min.js"></xhtml:script>-->
                <!--<xhtml:script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></xhtml:script>-->

                <!-- @see http://www.cssreset.com/scripts/yahoo-css-reset-yui-3/ -->
                <style type="text/css"><![CDATA[
                    /*
                    YUI 3.5.0 (build 5089)
                    Copyright 2012 Yahoo! Inc. All rights reserved.
                    Licensed under the BSD License.
                    http://yuilibrary.com/license/
                    */
                    html{color:#000;background:#FFF}body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,textarea,p,blockquote,th,td{margin:0;padding:0}table{border-collapse:collapse;border-spacing:0}fieldset,img{border:0}address,caption,cite,code,dfn,em,strong,th,var{font-style:normal;font-weight:normal}ol,ul{list-style:none}caption,th{text-align:left}h1,h2,h3,h4,h5,h6{font-size:100%;font-weight:normal}q:before,q:after{content:''}abbr,acronym{border:0;font-variant:normal}sup{vertical-align:text-top}sub{vertical-align:text-bottom}input,textarea,select{font-family:inherit;font-size:inherit;font-weight:inherit}input,textarea,select{*font-size:100%}legend{color:#000}#yui3-css-stamp.cssreset{display:none}
                ]]></style>
                <style type="text/css"><![CDATA[
                    /*
                    YUI 3.5.0 (build 5089)
                    Copyright 2012 Yahoo! Inc. All rights reserved.
                    Licensed under the BSD License.
                    http://yuilibrary.com/license/
                    */
                    h1{font-size:138.5%}h2{font-size:123.1%}h3{font-size:108%}h1,h2,h3{margin:1em 0}h1,h2,h3,h4,h5,h6,strong{font-weight:bold}abbr,acronym{border-bottom:1px dotted #000;cursor:help}em{font-style:italic}blockquote,ul,ol,dl{margin:1em}ol,ul,dl{margin-left:2em}ol{list-style:decimal outside}ul{list-style:disc outside}dl dd{margin-left:1em}th,td{border:1px solid #000;padding:.5em}th{font-weight:bold;text-align:center}caption{margin-bottom:.5em;text-align:center}p,fieldset,table,pre{margin-bottom:1em}input[type=text],input[type=password],textarea{width:12.25em;*width:11.9em}#yui3-css-stamp.cssbase{display:none}
                ]]></style>
                <style type="text/css"><![CDATA[
                    /*
                    YUI 3.5.0 (build 5089)
                    Copyright 2012 Yahoo! Inc. All rights reserved.
                    Licensed under the BSD License.
                    http://yuilibrary.com/license/
                    */
                    body{font:13px/1.231 arial,helvetica,clean,sans-serif;*font-size:small;*font:x-small}select,input,button,textarea{font:99% arial,helvetica,clean,sans-serif}table{font-size:inherit;font:100%}pre,code,kbd,samp,tt{font-family:monospace;*font-size:108%;line-height:100%}#yui3-css-stamp.cssfonts{display:none}
                ]]></style>


                <!--[if lt IE 9]>
                <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
                <![endif]-->

                <style type="text/css"><![CDATA[
                    /* Some of this is sticky-footer stuff borrowed from: http://stackoverflow.com/questions/17966140/twitter-bootstrap-3-sticky-footer */
                    html {
                        position: relative;
                        min-height: 100%;
                    }
                    body {
                        text-align: center;
                        /* Margin bottom by footer height */
                        margin-bottom: 60px;

                        font-family: Trebuchet, sans-serif;
                    }
                    .container {
                        margin: auto;
                        width: 8.5in;
                        padding: 5px;
                        text-align: left;
                    }
                    .header {
                        min-height: 200px;
                    }
                    #footer {
                        position: absolute;
                        font-size: 80%;
                        font-style: italic;
                        bottom: 0;
                        width: 90%;
                        /* Set the fixed height of the footer here */
                        /*height: 60px;*/
                        padding: 20px;
                        background-color: #f5f5f5;
                    }
                    #footer > .container {
                        padding-right: 15px;
                        padding-left: 15px;
                    }

                    h1, h2, h3, h4, h5, h6 {
                        color: #002b55;
                        margin: 0;
                        padding: 0;
                    }

                    hr {
                        margin: 0;
                    }

                    a {
                        color: #bca06e;
                        font-weight: bold;
                        text-decoration: none;
                    }
                    a:HOVER {
                        text-decoration: underline;
                        font-weight: bold;
                        color: #bca06e;
                    }
                    a:VISITED {
                        color: #bca06e;
                        font-weight: bold;
                    }

                    .topImageContainer {
                        width: 595px;
                    }
                    .topImageContainer a {
                        width: 594px;
                        z-index: -1;
                    }
                    .topImageContainer a img {
                        z-index: -1;
                    }

                    .tdNav {
                        float: right;
                        list-style: none;
                        margin: 0;
                        margin-top: 1em;
                        padding: 0;
                        z-index: 20;
                    }
                    .tdNav a {
                        z-index: 20;
                    }
                    .tdNav li {
                        margin-right: 1em;
                    }

                    .pageContainer {
                        margin-top: 2em;
                        margin-bottom: 2em;
                    }


                    .topLink {
                        float: right;
                        font-size: 10pt;
                    }



                    .unformattedList {
                        list-style: none;
                        margin: 0;
                        padding: 0;
                    }

                    .unstyledTable {
                        table-layout:fixed;
                        border-spacing: 0;
                        border-collapse: collapse;
                        width: 100%;
                        padding: 0;
                        margin: 0;
                    }
                    .unstyledTable th, .unstyledTable td {
                        vertical-align: top;
                        border: none;
                        padding: 0;
                        margin: 0;
                    }

                    p { margin: 0; }

                    .metadataLabel {
                        width: 200px;
                        font-weight: bold;
                    }

                    .metadataTable tr td {
                        padding-bottom: 5px;
                    }

                    .metadataTable tr.gapRow td {
                        padding-bottom: 10px;
                    }


                    .referenceContainer {
                        margin-bottom: 0.5em;
                    }

                    .referenceInfoTable .refLabelColumn {
                        width: 150px;
                    }

                    .referenceInfoTable .refValueColumn {
                        width: auto;
                        overflow: hidden;
                    }

                    .referenceInfoTable .refValueColumn a {
                        width: 100%;
                        display: block;
                        text-overflow: ellipsis;
                        overflow: hidden;
                    }

                    .refContainerOdd,
                    .refContainerEven {
                        padding: 5px;

                    }
                    .refContainerOdd {
                        background-color: #EEE;
                    }


                ]]></style>

<script type="text/javascript">
<xsl:text><![CDATA[
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');
ga('create', 'UA-46238754-1', 'auto');
ga('send', 'pageview');
]]></xsl:text>
</script>

                <xsl:comment>TODO: Insert additional javascript here</xsl:comment>

            </head>
            <body>

                <a name="top"></a>
                <div class="container">
                    <div class="header">
                        <ul class="tdNav">
                            <li><a href="#metadata">Metadata</a></li>
                            <li><a href="#expression">Expression</a></li>
                            <li><a href="#references">References</a></li>
                            <xsl:comment>TODO: Insert additional menu items here</xsl:comment>
                        </ul>

                        <div class="topImageContainer">
                            <a href="https://trustmark.gtri.gatech.edu" target="_blank">
                                <img src="data:image/png;base64,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" />
                            </a>
                        </div>
                    </div>

                    <h1>
                        Trust Interoperability Profile (TIP): <br />
                        <xsl:value-of select="string(./tf:Name)" />
                    </h1>

                    <div class="metadataContainer pageContainer">
                        <a name="metadata"><xsl:text> </xsl:text></a>
                        <h2>
                            Metadata
                            <div class="topLink">
                                <a href="#top">Top</a>
                            </div>
                        </h2>
                        <hr/>

                        <table class="unstyledTable metadataTable">
                            <xsl:call-template name="styleMetadata">
                                <xsl:with-param name="fieldName"><xsl:text>Identifier</xsl:text></xsl:with-param>
                                <xsl:with-param name="fieldValue" select="normalize-space(string(./tf:Identifier))" />
                            </xsl:call-template>
                            <xsl:call-template name="styleMetadata">
                                <xsl:with-param name="fieldName"><xsl:text>Name</xsl:text></xsl:with-param>
                                <xsl:with-param name="fieldValue" select="normalize-space(string(./tf:Name))" />
                            </xsl:call-template>
                            <xsl:call-template name="styleMetadata">
                                <xsl:with-param name="fieldName"><xsl:text>Version</xsl:text></xsl:with-param>
                                <xsl:with-param name="fieldValue" select="normalize-space(string(./tf:Version))" />
                            </xsl:call-template>
                            <xsl:call-template name="styleMetadata">
                                <xsl:with-param name="fieldName"><xsl:text>Publication Date</xsl:text></xsl:with-param>
                                <xsl:with-param name="fieldValue" select="substring(normalize-space(string(./tf:PublicationDateTime)), 0, 11)" />
                            </xsl:call-template>
                            <xsl:call-template name="styleMetadata">
                                <xsl:with-param name="fieldName"><xsl:text>Expiration Date</xsl:text></xsl:with-param>
                                <xsl:with-param name="fieldValue" select="substring(normalize-space(string(./tf:ExpirationDate)), 0, 11)" />
                            </xsl:call-template>

                            <tr>
                                <td class="metadataLabel">Issuing Organization</td>
                                <td class="metadataValue">

                                    <table class="unstyledTable metadataTable">

                                        <xsl:call-template name="styleMetadata">
                                            <xsl:with-param name="fieldName"><xsl:text>Identifier</xsl:text></xsl:with-param>
                                            <xsl:with-param name="fieldValue" select="normalize-space(string(./tf:Issuer/tf:Identifier))" />
                                        </xsl:call-template>
                                        <xsl:call-template name="styleMetadata">
                                            <xsl:with-param name="fieldName"><xsl:text>Name</xsl:text></xsl:with-param>
                                            <xsl:with-param name="fieldValue" select="normalize-space(string(./tf:Issuer/tf:Name))" />
                                        </xsl:call-template>

                                        <!-- Now iterate each contact -->
                                        <xsl:for-each select="./tf:Issuer/tf:Contact">
                                            <tr>
                                                <th colspan="2" style="border-bottom: 1px solid #888; text-align: left; padding-top: 1em;">
                                                    <xsl:value-of select="normalize-space(string(./tf:Kind))" /> Contact
                                                </th>
                                            </tr><tr>
                                            <td colspan="2">

                                                <table class="unstyledTable metadataTable">
                                                    <xsl:for-each select="./tf:Responder">
                                                        <xsl:call-template name="styleMetadata">
                                                            <xsl:with-param name="fieldName"><xsl:text>Responder</xsl:text></xsl:with-param>
                                                            <xsl:with-param name="fieldValue" select="normalize-space(string(.))" />
                                                        </xsl:call-template>
                                                    </xsl:for-each>
                                                    <xsl:for-each select="./tf:Email">
                                                        <xsl:call-template name="styleMetadata">
                                                            <xsl:with-param name="fieldName"><xsl:text>Email</xsl:text></xsl:with-param>
                                                            <xsl:with-param name="fieldValue" select="normalize-space(string(.))" />
                                                        </xsl:call-template>
                                                    </xsl:for-each>
                                                    <xsl:for-each select="./tf:Telephone">
                                                        <xsl:call-template name="styleMetadata">
                                                            <xsl:with-param name="fieldName"><xsl:text>Telephone</xsl:text></xsl:with-param>
                                                            <xsl:with-param name="fieldValue" select="normalize-space(string(.))" />
                                                        </xsl:call-template>
                                                    </xsl:for-each>
                                                    <xsl:for-each select="./tf:PhysicalAddress">
                                                        <xsl:call-template name="styleMetadata">
                                                            <xsl:with-param name="fieldName"><xsl:text>Physical Address</xsl:text></xsl:with-param>
                                                            <xsl:with-param name="fieldValue"><xsl:value-of select="normalize-space(string(.))" /></xsl:with-param>
                                                            <xsl:with-param name="fieldDisplayType"><xsl:text>MAPS</xsl:text></xsl:with-param>
                                                        </xsl:call-template>
                                                    </xsl:for-each>
                                                    <xsl:for-each select="./tf:MailingAddress">
                                                        <xsl:call-template name="styleMetadata">
                                                            <xsl:with-param name="fieldName"><xsl:text>Mailing Address</xsl:text></xsl:with-param>
                                                            <xsl:with-param name="fieldValue"><xsl:value-of select="normalize-space(string(.))" /></xsl:with-param>
                                                            <xsl:with-param name="fieldDisplayType"><xsl:text>MAPS</xsl:text></xsl:with-param>
                                                        </xsl:call-template>
                                                    </xsl:for-each>
                                                    <xsl:for-each select="./tf:WebsiteURL">
                                                        <xsl:call-template name="styleMetadata">
                                                            <xsl:with-param name="fieldName"><xsl:text>Website URL</xsl:text></xsl:with-param>
                                                            <xsl:with-param name="fieldValue" select="normalize-space(string(.))" />
                                                        </xsl:call-template>
                                                    </xsl:for-each>
                                                    <xsl:for-each select="./tf:Notes">
                                                        <xsl:call-template name="styleMetadata">
                                                            <xsl:with-param name="fieldName"><xsl:text>Notes</xsl:text></xsl:with-param>
                                                            <xsl:with-param name="fieldValue" select="normalize-space(string(.))" />
                                                        </xsl:call-template>
                                                    </xsl:for-each>
                                                </table>
                                            </td>
                                        </tr>
                                        </xsl:for-each>

                                    </table>

                                </td>
                            </tr>

                            <xsl:call-template name="styleMetadata">
                                <xsl:with-param name="fieldName"><xsl:text>Description</xsl:text></xsl:with-param>
                                <xsl:with-param name="fieldValue" select="normalize-space(string(./tf:Description))" />
                                <xsl:with-param name="fieldDisplayType"><xsl:text>Long</xsl:text></xsl:with-param>
                            </xsl:call-template>
                            <xsl:if test="string-length(string(./tf:LegalNotice)) > 0">
                                <xsl:call-template name="styleMetadata">
                                    <xsl:with-param name="fieldName"><xsl:text>Legal Notice</xsl:text></xsl:with-param>
                                    <xsl:with-param name="fieldValue" select="normalize-space(string(./tf:LegalNotice))" />
                                    <xsl:with-param name="fieldDisplayType"><xsl:text>Long</xsl:text></xsl:with-param>
                                </xsl:call-template>
                            </xsl:if>
                            <xsl:if test="string-length(string(./tf:Notes)) > 0">
                                <xsl:call-template name="styleMetadata">
                                    <xsl:with-param name="fieldName"><xsl:text>Notes</xsl:text></xsl:with-param>
                                    <xsl:with-param name="fieldValue" select="normalize-space(string(./tf:Notes))" />
                                    <xsl:with-param name="fieldDisplayType"><xsl:text>Long</xsl:text></xsl:with-param>
                                </xsl:call-template>
                            </xsl:if>
                            <tr class="gapRow"><td colspan="2"><xsl:text> </xsl:text></td></tr>


                        </table>
                    </div>

                    <div id="expressionContainer" class="pageContainer">
                        <a name="criteria"><xsl:text> </xsl:text></a>
                        <h2>
                            Trust Expression
                            <div class="topLink">
                                <a href="#top">Top</a>
                            </div>
                        </h2>
                        <hr />

                        <xsl:variable name="dslExpression" select="string(./tf:TrustExpression)" />
                        <code><xsl:value-of select="$dslExpression" /></code>
                    </div>


                    <div id="referencesContainer" class="pageContainer">
                        <a name="references"><xsl:text> </xsl:text></a>
                        <h2>
                            References (<xsl:value-of select="count(./tf:References/*)" />)
                            <div class="topLink">
                                <a href="#top">Top</a>
                            </div>
                        </h2>
                        <hr />

                        <xsl:for-each select="./tf:References/*">
                            <xsl:variable name="refContainerClassName">
                                <xsl:if test="(position() mod 2) = 1">
                                    <xsl:text>Odd</xsl:text>
                                </xsl:if>
                                <xsl:if test="(position() mod 2) != 1">
                                    <xsl:text>Even</xsl:text>
                                </xsl:if>
                            </xsl:variable>
                            <div class="refContainer{$refContainerClassName}">
                                <xsl:call-template name="styleReference">
                                    <xsl:with-param name="ref" select="." />
                                </xsl:call-template>
                            </div>
                        </xsl:for-each>

                    </div>

                </div>
                <div id="footer">
                    <!-- XSLT is the stupidest damn language ever: https://cygwin.com/ml/xsl-list/2002-02/msg00345.html -->
                    Copyright <xsl:text disable-output-escaping="yes">&amp;#169;</xsl:text> Georgia Tech Research Institute
                </div>

            </body>
        </html>
    </xsl:template>

    <xsl:template match="@*|node()">
        <!-- Ignore node output -->
    </xsl:template>


    <xsl:template name="styleMetadata">
        <xsl:param name="fieldName" />
        <xsl:param name="fieldValue" />
        <xsl:param name="fieldDisplayType" select="'Short'" /><!-- One of 'Short' or 'Long' -->

        <xsl:param name="fieldDisplayType2">
            <xsl:choose>
                <xsl:when test="starts-with($fieldValue, 'http')">
                    <xsl:text>URL</xsl:text>
                </xsl:when>
                <xsl:when test="contains($fieldValue, '@')">
                    <xsl:text>EMAIL</xsl:text>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$fieldDisplayType" />
                </xsl:otherwise>
            </xsl:choose>
        </xsl:param>


        <xsl:if test="$fieldValue != ' ' and $fieldValue != ''">
            <tr class="metadataRow">
                <td class="metadataLabel"><xsl:value-of select="$fieldName" /></td>
                <td class="metadataValue">
                    <xsl:choose>
                        <xsl:when test="$fieldDisplayType2 = 'URL'">
                            <a href="{$fieldValue}" target="_blank"><xsl:value-of select="$fieldValue" /></a>
                        </xsl:when>
                        <xsl:when test="$fieldDisplayType2 = 'EMAIL'">
                            <a href="mailto:{$fieldValue}"><xsl:value-of select="$fieldValue" /></a>
                        </xsl:when>
                        <xsl:when test="$fieldDisplayType2 = 'MAPS'">
                            <!-- Value should be URL encoded -->
                            <xsl:variable name="urlSafeAddr">
                                <xsl:call-template name="url-encode">
                                    <xsl:with-param name="str" select="$fieldValue"/>
                                </xsl:call-template>
                            </xsl:variable>
                            <a href="https://maps.google.com?q={$urlSafeAddr}" target="_blank"><xsl:value-of select="$fieldValue" /></a>
                        </xsl:when>
                        <xsl:otherwise><xsl:value-of select="$fieldValue" disable-output-escaping="yes" /></xsl:otherwise>
                    </xsl:choose>
                </td>
            </tr>
        </xsl:if>

    </xsl:template>


    <xsl:template name="styleReference">
        <xsl:param name="ref" />

        <xsl:variable name="xmlId" select="$ref/@tf:id" />
        <a target="{$xmlId}"><xsl:text> </xsl:text></a>
        <div class="referenceContainer">
            <h3>
                <xsl:if test="local-name($ref) = 'TrustmarkDefinitionRequirement'">Trustmark Definition Requirement</xsl:if>
                <xsl:if test="local-name($ref) = 'TrustInteroperabilityProfileReference'">Trust Interoperability Profile
Requirement</xsl:if>
                <span style="font-weight: normal; font-size: 80%; color: #BCA06E;">
                    [<xsl:value-of select="$xmlId" />]
                </span>
            </h3>

            <xsl:variable name="refName">
                <xsl:choose>
                    <xsl:when test="local-name($ref) = 'TrustmarkDefinitionRequirement'">
                        <xsl:value-of select="$ref/tf:TrustmarkDefinitionReference/tf:Name" />
                    </xsl:when>
                    <xsl:when test="local-name($ref) = 'TrustInteroperabilityProfileReference'">
                        <xsl:value-of select="$ref/tf:Name" />
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$ref/tf:Name" />
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:variable>
            <xsl:variable name="refVersion">
                <xsl:choose>
                    <xsl:when test="local-name($ref) = 'TrustmarkDefinitionRequirement'">
                        <xsl:value-of select="$ref/tf:TrustmarkDefinitionReference/tf:Version" />
                    </xsl:when>
                    <xsl:when test="local-name($ref) = 'TrustInteroperabilityProfileReference'">
                        <xsl:value-of select="$ref/tf:Version" />
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$ref/tf:Version" />
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:variable>
            <xsl:variable name="identifier">
                <xsl:choose>
                    <xsl:when test="local-name($ref) = 'TrustmarkDefinitionRequirement'">
                        <xsl:value-of select="$ref/tf:TrustmarkDefinitionReference/tf:Identifier" />
                    </xsl:when>
                    <xsl:when test="local-name($ref) = 'TrustInteroperabilityProfileReference'">
                        <xsl:value-of select="$ref/tf:Identifier" />
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$ref/tf:Identifier" />
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:variable>

            <div style="margin-left: 1em;">
                <table class="unstyledTable referenceInfoTable">
                    <xsl:if test="string-length($refName) > 0">
                        <tr>
                            <td class="refLabelColumn">Name</td>
                            <td class="refValueColumn"><xsl:value-of select="$refName" /></td>
                        </tr>
                    </xsl:if>

                    <xsl:if test="string-length($refVersion) > 0">
                        <tr>
                            <td class="refLabelColumn">Version</td>
                            <td class="refValueColumn"><xsl:value-of select="$refVersion" /></td>
                        </tr>
                    </xsl:if>

                    <tr>
                        <td class="refLabelColumn">Identifier</td>
                        <td class="refValueColumn">
                            <a href="{$identifier}">
                                <xsl:value-of select="$identifier" />
                            </a>
                        </td>
                    </tr>

                    <xsl:if test="local-name($ref) = 'TrustmarkDefinitionRequirement'">
                        <tr>
                            <td class="refLabelColumn">Accepted Providers</td>
                            <td class="refValueColumn">
                                <xsl:choose>
                                    <xsl:when test="count($ref/tf:ProviderReference) &gt; 0">
                                        <xsl:for-each select="$ref/tf:ProviderReference">
                                            <xsl:call-template name="outputProviderId">
                                                <xsl:with-param name="ref" select="." />
                                            </xsl:call-template>
                                        </xsl:for-each>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <div><em>ALL providers accepted.</em></div>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </td>
                        </tr>
                    </xsl:if>

                </table>

            </div>
        </div>
    </xsl:template>

    <xsl:template name="outputProviderId">
        <xsl:param name="ref"></xsl:param>

        <xsl:variable name="identifierValue">
            <xsl:choose>
                <xsl:when test="string-length(string($ref/@tf:ref)) > 0">
                    <xsl:value-of select="string(//tf:ProviderReference[./@tf:id = $ref/@tf:ref]/tf:Identifier)" />
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="string($ref/tf:Identifier)" />
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <div>
            <a href="{$identifierValue}">
                <xsl:value-of select="$identifierValue" />
            </a>
        </div>
    </xsl:template>




    <!--
        This stylesheet to perform URL encoding was "borrowed" from:
        http://stackoverflow.com/questions/2425516/xslt-version-1-url-encoding

        Props to the author.
     -->


    <!-- Characters we'll support.
         We could add control chars 0-31 and 127-159, but we won't. -->
    <xsl:variable name="ascii"> !"#$%&amp;'()*+,-./0123456789:;&lt;=&gt;?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~</xsl:variable>
    <xsl:variable name="latin1">&#160;&#161;&#162;&#163;&#164;&#165;&#166;&#167;&#168;&#169;&#170;&#171;&#172;&#173;&#174;&#175;&#176;&#177;&#178;&#179;&#180;&#181;&#182;&#183;&#184;&#185;&#186;&#187;&#188;&#189;&#190;&#191;&#192;&#193;&#194;&#195;&#196;&#197;&#198;&#199;&#200;&#201;&#202;&#203;&#204;&#205;&#206;&#207;&#208;&#209;&#210;&#211;&#212;&#213;&#214;&#215;&#216;&#217;&#218;&#219;&#220;&#221;&#222;&#223;&#224;&#225;&#226;&#227;&#228;&#229;&#230;&#231;&#232;&#233;&#234;&#235;&#236;&#237;&#238;&#239;&#240;&#241;&#242;&#243;&#244;&#245;&#246;&#247;&#248;&#249;&#250;&#251;&#252;&#253;&#254;&#255;</xsl:variable>

    <!-- Characters that usually don't need to be escaped -->
    <xsl:variable name="safe">!'()*-.0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz~</xsl:variable>

    <xsl:variable name="hex" >0123456789ABCDEF</xsl:variable>

    <!--<xsl:template match="/">-->

    <!--<result>-->
    <!--<string>-->
    <!--<xsl:value-of select="$iso-string"/>-->
    <!--</string>-->
    <!--<hex>-->
    <!--<xsl:call-template name="url-encode">-->
    <!--<xsl:with-param name="str" select="$iso-string"/>-->
    <!--</xsl:call-template>-->
    <!--</hex>-->
    <!--</result>-->

    <!--</xsl:template>-->

    <xsl:template name="url-encode">
        <xsl:param name="str"/>
        <xsl:if test="$str">
            <xsl:variable name="first-char" select="substring($str,1,1)"/>
            <xsl:choose>
                <xsl:when test="contains($safe,$first-char)">
                    <xsl:value-of select="$first-char"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:variable name="codepoint">
                        <xsl:choose>
                            <xsl:when test="contains($ascii,$first-char)">
                                <xsl:value-of select="string-length(substring-before($ascii,$first-char)) + 32"/>
                            </xsl:when>
                            <xsl:when test="contains($latin1,$first-char)">
                                <xsl:value-of select="string-length(substring-before($latin1,$first-char)) + 160"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:message terminate="no">Warning: string contains a character that is out of range! Substituting "?".</xsl:message>
                                <xsl:text>63</xsl:text>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:variable>
                    <xsl:variable name="hex-digit1" select="substring($hex,floor($codepoint div 16) + 1,1)"/>
                    <xsl:variable name="hex-digit2" select="substring($hex,$codepoint mod 16 + 1,1)"/>
                    <xsl:value-of select="concat('%',$hex-digit1,$hex-digit2)"/>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:if test="string-length($str) &gt; 1">
                <xsl:call-template name="url-encode">
                    <xsl:with-param name="str" select="substring($str,2)"/>
                </xsl:call-template>
            </xsl:if>
        </xsl:if>
    </xsl:template>


</xsl:stylesheet>
