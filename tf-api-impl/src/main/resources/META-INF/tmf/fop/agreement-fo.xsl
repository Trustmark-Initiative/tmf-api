<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="3.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fn="http://www.w3.org/2005/xpath-functions"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:fox="http://xmlgraphics.apache.org/fop/extensions"
                xmlns:pdf="http://xmlgraphics.apache.org/fop/extensions/pdf"
                xmlns:tf="https://trustmarkinitiative.org/specifications/trustmark-framework/1.3/schema/"
>
    <!--
        xmlns:fo schema location: /tmf-api/tf-api-impl/src/main/resources/META-INF/xml-schema/fop.xsd
        [Source](http://svn.apache.org/viewvc/xmlgraphics/fop/trunk/fop/src/foschema/fop.xsd?view=co)
        
        See also:
        [FOP Conformance to XSL-FO 1.1](https://xmlgraphics.apache.org/fop/compliance.html)
        [Basic Help for Using XML, XSLT, and XSL-FO](https://xmlgraphics.apache.org/fop/fo.html)
    -->
    <xsl:output method="xml" indent="yes"/>
    
    <!-- ========== Keys for xref ========== -->
    <xsl:key name="responsibility-by-id" match="/tf:Agreement/tf:AgreementResponsibilities/tf:AgreementResponsibility" use="@tf:id"/>
    <xsl:key name="supplemented-tip-snapshot-by-id" match="//tf:SupplementedTIPSnapshots/tf:SupplementedTIPSnapshot" use="@tf:id"/>
    
    <!-- ========== Root Template ========== -->
    <xsl:template match="/tf:Agreement">
        <fo:root>
            <!-- ======== Layout ======== -->
            <fo:layout-master-set>
                <fo:simple-page-master master-name="US-Letter-Portrait"
                                       page-width="8.5in"
                                       page-height="11in"
                                       margin="1in"
                >
                    <fo:region-body/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <!-- ======== Content ======== -->
            <xsl:variable name="attachment-count" select="count(tf:AgreementAttachments/tf:AgreementAttachment)"/>
            <fo:page-sequence master-reference="US-Letter-Portrait">
                <fo:flow flow-name="xsl-region-body">
                    
                    <!-- ===== Title and Version ===== -->
                    <fo:block text-align="center"
                              font-weight="bold"
                              font-size="14pt"
                              line-height="17pt"
                              space-after="12pt"
                              keep-with-next="always"
                    >
                        <xsl:call-template name="xhtml-text">
                            <xsl:with-param name="root" select="tf:Title"/>
                        </xsl:call-template>
                    </fo:block>
                    
                    <!-- ===== Other Metadata ===== -->
                    <fo:table table-layout="fixed">
                        <fo:table-column column-width="150pt"/>
                        <fo:table-column column-width="proportional-column-width(1)"/>
                        <fo:table-body>
                            <xsl:call-template name="table-row-name-value-line">
                                <xsl:with-param name="name-weight">normal</xsl:with-param>
                                <xsl:with-param name="name">Document Metadata:</xsl:with-param>
                                <xsl:with-param name="value"/>
                            </xsl:call-template>
                            <xsl:call-template name="table-row-name-value-line">
                                <xsl:with-param name="name">Identifier</xsl:with-param>
                                <xsl:with-param name="is-value-html">true</xsl:with-param>
                                <xsl:with-param name="value">
                                    <xsl:call-template name="html-identifier">
                                        <xsl:with-param name="value" select="tf:Identifier"/>
                                    </xsl:call-template>
                                </xsl:with-param>
                            </xsl:call-template>
                            <xsl:call-template name="table-row-name-value-line">
                                <xsl:with-param name="name">Version</xsl:with-param>
                                <xsl:with-param name="value" select="tf:Version"/>
                            </xsl:call-template>
                            <xsl:call-template name="table-row-name-value-line">
                                <xsl:with-param name="name">Created Timestamp</xsl:with-param>
                                <xsl:with-param name="value" select="tf:CreationDateTime"/>
                            </xsl:call-template>
                            <xsl:call-template name="table-row-name-value-line">
                                <xsl:with-param name="name">Effective Timestamp</xsl:with-param>
                                <xsl:with-param name="is-value-html">true</xsl:with-param>
                                <xsl:with-param name="value">
                                    <xsl:call-template name="html-timestamp-or-specified-in-legal-text">
                                        <xsl:with-param name="value" select="tf:Duration/tf:EffectiveDateTime"/>
                                    </xsl:call-template>
                                </xsl:with-param>
                            </xsl:call-template>
                            <xsl:call-template name="table-row-name-value-line">
                                <xsl:with-param name="name">Termination Timestamp</xsl:with-param>
                                <xsl:with-param name="is-value-html">true</xsl:with-param>
                                <xsl:with-param name="value">
                                    <xsl:call-template name="html-timestamp-or-specified-in-legal-text">
                                        <xsl:with-param name="value" select="tf:Duration/tf:TerminationDateTime"/>
                                    </xsl:call-template>
                                </xsl:with-param>
                            </xsl:call-template>
                            <xsl:call-template name="table-row-name-value-line">
                                <xsl:with-param name="name">Description</xsl:with-param>
                                <xsl:with-param name="is-value-html">true</xsl:with-param>
                                <xsl:with-param name="value" select="tf:Description"/>
                            </xsl:call-template>
                        </fo:table-body>
                    </fo:table>
    
                    <!-- ===== How-to Page ===== -->
                    <fo:block page-break-before="always"/>
                    <xsl:call-template name="section-header">
                        <xsl:with-param name="text">Sections of this Document</xsl:with-param>
                    </xsl:call-template>
                    <fo:block>
                        This document contains the following sections, in order.
                    </fo:block>
                    
                    <xsl:call-template name="subsection-header">
                        <xsl:with-param name="text">Non-Binding Sections</xsl:with-param>
                    </xsl:call-template>
                    <fo:block>
                        <xsl:variable name="non-binding-section-count" select="count(tf:AgreementNonBindingSections/tf:AgreementNonBindingSection)"/>
                        Introductory material describing the purpose and spirit of the agreement.
                        The names of these sections are determined by the authors of the agreement.
                        This document contains
                        <xsl:value-of select="$non-binding-section-count"/>
                        <xsl:text> non-binding section</xsl:text>
                        <xsl:if test="not($non-binding-section-count=1)">
                            <xsl:text>s</xsl:text>
                        </xsl:if>
                        <xsl:text>.</xsl:text>
                    </fo:block>
                    
                    <xsl:call-template name="subsection-header">
                        <xsl:with-param name="text">Legal Sections</xsl:with-param>
                    </xsl:call-template>
                    <fo:block>
                        Legally substantive terms and conditions that pertain to one or more parties to the agreement,
                        and supplement the technical requirements of each party. Each of these sections may contain
                        any number of levels of nested subsections.
                    </fo:block>
                    
                    <xsl:call-template name="subsection-header">
                        <xsl:with-param name="text">Responsibilities</xsl:with-param>
                    </xsl:call-template>
                    <fo:block>
                        Well-defined functions to be filled by parties within the context of the agreement,
                        and explicit sets of technical requirements for each function. Each responsibility is
                        associated with a list of TIPs. Each of the associated TIPs can have supplemental legal text,
                        which provides flexibility to modify the requirements defined by the TIP.
                    </fo:block>
                    
                    <xsl:call-template name="subsection-header">
                        <xsl:with-param name="text">Parties</xsl:with-param>
                    </xsl:call-template>
                    <fo:block>
                        The organizations that intend to participate in the execution of this agreement. This section
                        contains a list of the responsibilities assigned to each party. This section also includes
                        abbreviations used for each party throughout the agreement as well as contact information.
                    </fo:block>
                    
                    <xsl:call-template name="subsection-header">
                        <xsl:with-param name="text">Defined Terms</xsl:with-param>
                    </xsl:call-template>
                    <fo:block>
                        A dictionary of defined and abbreviated words and phrases relevant to the document.
                    </fo:block>
                    
                    <xsl:call-template name="subsection-header">
                        <xsl:with-param name="text">Signatures</xsl:with-param>
                    </xsl:call-template>
                    <fo:block>
                        Section of the agreement in which the parties indicate their
                        legal acceptance of and consent to the agreement.
                    </fo:block>

                    <xsl:call-template name="subsection-header">
                        <xsl:with-param name="text">Attachments</xsl:with-param>
                    </xsl:call-template>
                    <fo:block>
                        Additional data (typically image data) that is attached to this agreement.
                        This document contains
                        <xsl:value-of select="$attachment-count"/>
                        <xsl:text> attachment</xsl:text>
                        <xsl:if test="not($attachment-count=1)">
                            <xsl:text>s</xsl:text>
                        </xsl:if>
                        <xsl:text>.</xsl:text>
                    </fo:block>
                    
                    <xsl:call-template name="subsection-header">
                        <xsl:with-param name="text"> </xsl:with-param>
                    </xsl:call-template>
                    
                    <fo:block>
                        <fo:leader leader-pattern="rule" leader-length="100%"/>
                    </fo:block>
                    <fo:block text-align="right">
                        The content of the agreement begins at the start of the next page.
                    </fo:block>
    
                    <!-- ===== Non-binding Sections ===== -->
                    <fo:block page-break-before="always"/>
                    <xsl:for-each select="tf:AgreementNonBindingSections/tf:AgreementNonBindingSection">
                        <xsl:sort select="tf:Index" data-type="number"/>
                        <xsl:call-template name="section-header">
                            <xsl:with-param name="text">
                                <xsl:call-template name="xhtml-text">
                                    <xsl:with-param name="root" select="tf:Title"/>
                                </xsl:call-template>
                            </xsl:with-param>
                        </xsl:call-template>
                        <fo:block text-indent="0.5in" space-after="12pt">
                            <xsl:call-template name="xhtml-text">
                                <xsl:with-param name="root" select="tf:Text"/>
                            </xsl:call-template>
                        </fo:block>
                    </xsl:for-each>
    
                    <!-- ===== Legal Sections ===== -->
                    <fo:block page-break-before="always"/>
                    <xsl:call-template name="section-header">
                        <xsl:with-param name="text">Legal Sections</xsl:with-param>
                    </xsl:call-template>
                    <xsl:if test="count(tf:AgreementLegalSections/tf:AgreementLegalSection) > 0">
                        <fo:list-block>
                            <xsl:for-each select="tf:AgreementLegalSections/tf:AgreementLegalSection">
                                <xsl:sort select="tf:Index" data-type="number"/>
                                <xsl:call-template name="legal-section">
                                    <xsl:with-param name="list-style-type">
                                        <xsl:value-of select="../../tf:LegalSectionListStyleType"/>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </xsl:for-each>
                        </fo:list-block>
                    </xsl:if>
    
                    <!-- ===== Responsibilities ===== -->
                    <fo:block page-break-before="always"/>
                    <xsl:call-template name="section-header">
                        <xsl:with-param name="text">Responsibilities</xsl:with-param>
                    </xsl:call-template>
                    <xsl:for-each select="tf:AgreementResponsibilities/tf:AgreementResponsibility">
                        <!-- See here: http://stackoverflow.com/questions/22018606/xsl-sort-treats-lowercase-separately-from-uppercase -->
                        <xsl:sort select="translate(tf:Name, 'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')" order="ascending" />
                        <fo:block space-before="14pt"
                                  font-weight="bold"
                                  font-size="13pt"
                                  line-height="16pt"
                                  keep-with-next="always"
                        >
                            <xsl:value-of select="tf:Name"/>
                        </fo:block>
                        <fo:table table-layout="fixed">
                            <fo:table-column column-width="60pt"/>
                            <fo:table-column column-width="proportional-column-width(1)"/>
                            <fo:table-body start-indent="0pt">
                                <xsl:call-template name="table-row-name-value-line">
                                    <xsl:with-param name="name">Category</xsl:with-param>
                                    <xsl:with-param name="value" select="tf:Category"/>
                                </xsl:call-template>
                                <xsl:call-template name="table-row-name-value-line">
                                    <xsl:with-param name="name">Definition</xsl:with-param>
                                    <xsl:with-param name="is-value-html">true</xsl:with-param>
                                    <xsl:with-param name="value" select="tf:Definition"/>
                                </xsl:call-template>
                                <fo:table-row border-bottom="1px solid black">
                                    <fo:table-cell>
                                        <fo:block font-weight="bold">
                                            TIPs
                                            (<xsl:value-of select="count(tf:SupplementedTIPSnapshots/tf:SupplementedTIPSnapshot)"/>)
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block>
                                            <xsl:if test="count(tf:SupplementedTIPSnapshots/tf:SupplementedTIPSnapshot) > 0">
                                                <fo:list-block>
                                                    <xsl:for-each select="tf:SupplementedTIPSnapshots/tf:SupplementedTIPSnapshot">
                                                        <fo:list-item space-after="12pt">
                                                            <fo:list-item-label end-indent="label-end()">
                                                                <fo:block font-weight="bold">
                                                                    <xsl:number value="position()" format="1. "/>
                                                                </fo:block>
                                                            </fo:list-item-label>
                                                            <fo:list-item-body start-indent="body-start() - 6pt">
                                                                <fo:block>
                                                                    <xsl:call-template name="supplemented-tip-snapshot">
                                                                        <xsl:with-param name="id-ref">
                                                                            <xsl:choose>
                                                                                <xsl:when test="not(normalize-space(@tf:id)='')">
                                                                                    <xsl:value-of select="@tf:id"/>
                                                                                </xsl:when>
                                                                                <xsl:when test="not(normalize-space(@tf:ref)='')">
                                                                                    <xsl:value-of select="@tf:ref"/>
                                                                                </xsl:when>
                                                                                <xsl:otherwise>
                                                                                    UNKNOWN
                                                                                </xsl:otherwise>
                                                                            </xsl:choose>
                                                                        </xsl:with-param>
                                                                    </xsl:call-template>
                                                                </fo:block>
                                                            </fo:list-item-body>
                                                        </fo:list-item>
                                                    </xsl:for-each>
                                                </fo:list-block>
                                            </xsl:if>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </xsl:for-each>
    
                    <!-- ===== Parties ===== -->
                    <fo:block page-break-before="always"/>
                    <xsl:call-template name="section-header">
                        <xsl:with-param name="text">Parties</xsl:with-param>
                    </xsl:call-template>
                    <xsl:for-each select="tf:AgreementParties/tf:AgreementParty">
                        <xsl:sort select="tf:Organization/tf:Identifier" data-type="text"/>
                        <fo:block space-before="14pt"
                                  font-weight="bold"
                                  font-size="13pt"
                                  line-height="16pt"
                                  keep-with-next="always"
                        >
                            Party #<xsl:value-of select="position()"/>
                        </fo:block>
                        <fo:table table-layout="fixed">
                            <fo:table-column column-width="100pt"/>
                            <fo:table-column column-width="proportional-column-width(1)"/>
                            <fo:table-body start-indent="0pt">
                                <xsl:call-template name="table-row-name-value-line">
                                    <xsl:with-param name="name">Abbreviation</xsl:with-param>
                                    <xsl:with-param name="value" select="tf:AbbreviatedName"/>
                                </xsl:call-template>
                                <fo:table-row border-bottom="1px solid black">
                                    <fo:table-cell>
                                        <fo:block font-weight="bold">
                                            Organization
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <xsl:for-each select="tf:Organization[1]">
                                            <fo:block>
                                                <fo:table table-layout="fixed">
                                                    <fo:table-column column-width="100pt"/>
                                                    <fo:table-column column-width="proportional-column-width(1)"/>
                                                    <fo:table-body start-indent="0pt">
                                                        <xsl:call-template name="table-row-name-value">
                                                            <xsl:with-param name="name">Identifier</xsl:with-param>
                                                            <xsl:with-param name="is-value-html">true</xsl:with-param>
                                                            <xsl:with-param name="value">
                                                                <xsl:call-template name="html-identifier">
                                                                    <xsl:with-param name="value" select="tf:Identifier"/>
                                                                </xsl:call-template>
                                                            </xsl:with-param>
                                                        </xsl:call-template>
                                                        <xsl:call-template name="table-row-name-value">
                                                            <xsl:with-param name="name">Name</xsl:with-param>
                                                            <xsl:with-param name="value" select="tf:Name"/>
                                                        </xsl:call-template>
                                                        <fo:table-row border-bottom="1px solid black">
                                                            <fo:table-cell number-columns-spanned="2">
                                                                <fo:block font-weight="bold">
                                                                    PRIMARY Contact
                                                                </fo:block>
                                                            </fo:table-cell>
                                                        </fo:table-row>
                                                        <xsl:for-each select="tf:Contact[tf:Kind='PRIMARY']">
                                                            <xsl:call-template name="table-row-name-value">
                                                                <xsl:with-param name="name">Responder</xsl:with-param>
                                                                <xsl:with-param name="value" select="tf:Responder"/>
                                                            </xsl:call-template>
                                                            <xsl:call-template name="table-row-name-value">
                                                                <xsl:with-param name="name">Email</xsl:with-param>
                                                                <xsl:with-param name="value" select="tf:Email"/>
                                                            </xsl:call-template>
                                                            <xsl:call-template name="table-row-name-value">
                                                                <xsl:with-param name="name">Telephone</xsl:with-param>
                                                                <xsl:with-param name="value" select="tf:Telephone"/>
                                                            </xsl:call-template>
                                                            <xsl:call-template name="table-row-name-value">
                                                                <xsl:with-param name="name">PhysicalAddress</xsl:with-param>
                                                                <xsl:with-param name="value" select="tf:PhysicalAddress"/>
                                                            </xsl:call-template>
                                                            <xsl:call-template name="table-row-name-value">
                                                                <xsl:with-param name="name">MailingAddress</xsl:with-param>
                                                                <xsl:with-param name="value" select="tf:MailingAddress"/>
                                                            </xsl:call-template>
                                                        </xsl:for-each>
                                                    </fo:table-body>
                                                </fo:table>
                                                <fo:block font-weight="bold">
                                                    Notes
                                                </fo:block>
                                                <fo:block>
                                                    <xsl:call-template name="xhtml-text">
                                                        <xsl:with-param name="root" select="tf:Contact[tf:Kind='PRIMARY']/tf:Notes"/>
                                                    </xsl:call-template>
                                                </fo:block>
                                            </fo:block>
                                        </xsl:for-each>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row border-bottom="1px solid black">
                                    <fo:table-cell>
                                        <fo:block font-weight="bold">
                                            Responsibility Assignments
                                            (<xsl:value-of select="count(tf:PartyResponsibilities/tf:AgreementResponsibility)"/>)
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block>
                                            <xsl:if test="count(tf:PartyResponsibilities/tf:AgreementResponsibility) > 0">
                                                <fo:list-block>
                                                    <xsl:for-each select="tf:PartyResponsibilities/tf:AgreementResponsibility">
                                                        <xsl:sort select="translate(key('responsibility-by-id', @tf:ref)/tf:Name, 'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')" order="ascending" />
                                                        <fo:list-item>
                                                            <fo:list-item-label end-indent="label-end()">
                                                                <fo:block>&#x2022;</fo:block>
                                                            </fo:list-item-label>
                                                            <fo:list-item-body start-indent="body-start() - 12pt">
                                                                <fo:block>
                                                                    <xsl:value-of select="key('responsibility-by-id', @tf:ref)/tf:Name"/>
                                                                </fo:block>
                                                            </fo:list-item-body>
                                                        </fo:list-item>
                                                    </xsl:for-each>
                                                </fo:list-block>
                                            </xsl:if>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </xsl:for-each>
    
                    <!-- ===== Defined Terms ===== -->
                    <fo:block page-break-before="always"/>
                    <xsl:call-template name="section-header">
                        <xsl:with-param name="text">Defined Terms</xsl:with-param>
                    </xsl:call-template>
                    <xsl:for-each select="tf:AgreementTerms/tf:Term">
                        <xsl:sort select="tf:Name" data-type="text"/>
                        <fo:block space-before="6pt">
                            <fo:inline font-weight="bold">
                                <xsl:value-of select="tf:Name"/>
                            </fo:inline>
                            <xsl:if test="count(tf:Abbreviation) > 0">
                                <xsl:text> (</xsl:text>
                                <xsl:for-each select="tf:Abbreviation">
                                    <xsl:sort select="." data-type="text"/>
                                    <xsl:value-of select="."/>
                                    <xsl:if test="position() != last()">
                                        <xsl:text>, </xsl:text>
                                    </xsl:if>
                                </xsl:for-each>
                                <xsl:text>)</xsl:text>
                            </xsl:if>
                        </fo:block>
                        <fo:block>
                            <xsl:value-of select="tf:Definition"/>
                        </fo:block>
                    </xsl:for-each>
    
                    <!-- ===== Signatures ===== -->
                    <fo:block page-break-before="always"/>
                    <xsl:call-template name="section-header">
                        <xsl:with-param name="text">Signatures</xsl:with-param>
                    </xsl:call-template>
                    <fo:block>
                        <xsl:call-template name="xhtml-text">
                            <xsl:with-param name="root" select="tf:SignaturePageText"/>
                        </xsl:call-template>
                    </fo:block>
                    <xsl:for-each select="tf:AgreementParties/tf:AgreementParty">
                        <xsl:sort select="tf:Organization/tf:Identifier" data-type="text"/>
                            <fo:table table-layout="fixed" space-before="0.6in">
                                <fo:table-column column-width="proportional-column-width(1)"/>
                                <fo:table-column column-width="0.5in"/>
                                <fo:table-column column-width="proportional-column-width(1)"/>
                                <fo:table-body>
                                    <fo:table-row>
                                        <fo:table-cell>
                                            <fo:block>
                                                <xsl:value-of select="tf:Organization/tf:Name"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell>
                                            <fo:block/>
                                        </fo:table-cell>
                                        <fo:table-cell>
                                            <fo:block/>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <xsl:for-each select="tf:PartySignatories/tf:AgreementSignatory">
                                        <fo:table-row height="3em">
                                            <fo:table-cell>
                                                <fo:block/>
                                            </fo:table-cell>
                                            <fo:table-cell>
                                                <fo:block/>
                                            </fo:table-cell>
                                            <fo:table-cell>
                                                <fo:block/>
                                            </fo:table-cell>
                                        </fo:table-row>
                                        <fo:table-row>
                                            <fo:table-cell>
                                                <fo:list-block>
                                                    <fo:list-item>
                                                        <fo:list-item-label end-indent="label-end()">
                                                            <fo:block>By:</fo:block>
                                                        </fo:list-item-label>
                                                        <fo:list-item-body start-indent="body-start()">
                                                            <fo:block>
                                                                <fo:leader leader-pattern="rule" leader-length="100%"/>
                                                            </fo:block>
                                                        </fo:list-item-body>
                                                    </fo:list-item>
                                                    <fo:list-item>
                                                        <fo:list-item-label end-indent="label-end()">
                                                            <fo:block/>
                                                        </fo:list-item-label>
                                                        <fo:list-item-body start-indent="body-start()">
                                                            <fo:block>
                                                                <xsl:value-of select="tf:IndividualName"/>
                                                            </fo:block>
                                                        </fo:list-item-body>
                                                    </fo:list-item>
                                                    <fo:list-item>
                                                        <fo:list-item-label end-indent="label-end()">
                                                            <fo:block/>
                                                        </fo:list-item-label>
                                                        <fo:list-item-body start-indent="body-start()">
                                                            <fo:block>
                                                                <xsl:value-of select="tf:IndividualTitle"/>
                                                            </fo:block>
                                                        </fo:list-item-body>
                                                    </fo:list-item>
                                                    <fo:list-item>
                                                        <fo:list-item-label end-indent="label-end()">
                                                            <fo:block/>
                                                        </fo:list-item-label>
                                                        <fo:list-item-body start-indent="body-start()">
                                                            <fo:block>
                                                                <xsl:value-of select="tf:DivisionName"/>
                                                            </fo:block>
                                                        </fo:list-item-body>
                                                    </fo:list-item>
                                                    <fo:list-item>
                                                        <fo:list-item-label end-indent="label-end()">
                                                            <fo:block/>
                                                        </fo:list-item-label>
                                                        <fo:list-item-body start-indent="body-start()">
                                                            <fo:block>
                                                                <xsl:value-of select="tf:AuxiliaryText"/>
                                                            </fo:block>
                                                        </fo:list-item-body>
                                                    </fo:list-item>
                                                </fo:list-block>
                                            </fo:table-cell>
                                            <fo:table-cell>
                                                <fo:block/>
                                            </fo:table-cell>
                                            <fo:table-cell>
                                                <fo:list-block>
                                                    <fo:list-item>
                                                        <fo:list-item-label end-indent="0.5in">
                                                            <fo:block>Date:</fo:block>
                                                        </fo:list-item-label>
                                                        <fo:list-item-body start-indent="0.5in">
                                                            <fo:block>
                                                                <fo:leader leader-pattern="rule" leader-length="100%"/>
                                                            </fo:block>
                                                        </fo:list-item-body>
                                                    </fo:list-item>
                                                </fo:list-block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                    </xsl:for-each>

                    <!-- ===== Attachments ===== -->
                    <fo:block page-break-before="always"/>
                    <xsl:call-template name="section-header">
                        <xsl:with-param name="text">Attachments</xsl:with-param>
                    </xsl:call-template>
                    <fo:block>
                        This document contains
                        <xsl:value-of select="$attachment-count"/>
                        <xsl:text> attachment</xsl:text>
                        <xsl:if test="not($attachment-count=1)">
                            <xsl:text>s</xsl:text>
                        </xsl:if>
                        <xsl:text>.</xsl:text>
                    </fo:block>

                    <xsl:if test="$attachment-count > 0">

                        <xsl:call-template name="subsection-header">
                            <xsl:with-param name="text"> </xsl:with-param>
                        </xsl:call-template>

                        <fo:block>
                            <fo:leader leader-pattern="rule" leader-length="100%"/>
                        </fo:block>
                        <fo:block text-align="right">
                            Attachment data begins on the next page.
                        </fo:block>
                    </xsl:if>
                </fo:flow>
            </fo:page-sequence>

            <xsl:if test="$attachment-count > 0">
                <xsl:for-each select="tf:AgreementAttachments/tf:AgreementAttachment">
                    <xsl:sort select="tf:Index" data-type="number"/>
                    <xsl:variable name="data-uri">
                        <xsl:text>url('data:</xsl:text>
                        <xsl:value-of select="tf:MimeType"/>
                        <xsl:text>;base64,</xsl:text>
                        <xsl:value-of select="tf:DataBase64"/>
                        <xsl:text>')</xsl:text>
                    </xsl:variable>
                    <fo:page-sequence master-reference="US-Letter-Portrait">
                        <fo:flow flow-name="xsl-region-body">
                            <fo:block page-break-before="always">
                                <xsl:call-template name="subsection-header">
                                    <xsl:with-param name="text">
                                        <xsl:text>Attachment </xsl:text>
                                        <xsl:value-of select="position()"/>
                                        <xsl:text> of </xsl:text>
                                        <xsl:value-of select="$attachment-count"/>
                                    </xsl:with-param>
                                </xsl:call-template>

                                <fo:table table-layout="fixed">
                                    <fo:table-column column-width="150pt"/>
                                    <fo:table-column column-width="proportional-column-width(1)"/>
                                    <fo:table-body>
                                        <xsl:call-template name="table-row-name-value-line">
                                            <xsl:with-param name="name">Name</xsl:with-param>
                                            <xsl:with-param name="is-value-html">true</xsl:with-param>
                                            <xsl:with-param name="value" select="tf:Name"/>
                                        </xsl:call-template>
                                        <xsl:call-template name="table-row-name-value-line">
                                            <xsl:with-param name="name">Description</xsl:with-param>
                                            <xsl:with-param name="is-value-html">true</xsl:with-param>
                                            <xsl:with-param name="value" select="tf:Description"/>
                                        </xsl:call-template>
                                        <xsl:call-template name="table-row-name-value-line">
                                            <xsl:with-param name="name">Content Type</xsl:with-param>
                                            <xsl:with-param name="value" select="tf:MimeType"/>
                                        </xsl:call-template>
                                    </fo:table-body>
                                </fo:table>
                            </fo:block>
                            <xsl:if test="string(tf:MimeType) = 'application/pdf'">
                                <fo:block text-align="center" space-before="3em">
                                    Attachment
                                    <xsl:value-of select="position()"/>
                                    begins on the next page.
                                </fo:block>
                            </xsl:if>
                            <xsl:if test="string(tf:MimeType) != 'application/pdf'">
                                <fo:block text-align="center">
                                    <fo:external-graphic
                                            src=""
                                            width="100%"
                                            content-height="100%"
                                            content-width="scale-to-fit"
                                            scaling="uniform">
                                        <xsl:attribute name="src">
                                            <xsl:value-of select="$data-uri"/>
                                        </xsl:attribute>
                                    </fo:external-graphic>
                                </fo:block>
                            </xsl:if>
                        </fo:flow>
                    </fo:page-sequence>
                    <xsl:if test="string(tf:MimeType) = 'application/pdf'">
                        <fox:external-document content-type="pdf" src="">
                            <xsl:attribute name="src">
                                <xsl:value-of select="$data-uri"/>
                            </xsl:attribute>
                        </fox:external-document>
                    </xsl:if>
                </xsl:for-each>
            </xsl:if>

        </fo:root>
    </xsl:template>
    
    <!-- ========== Inner Templates ========== -->
    <xsl:template name="table-row-name-value-line">
        <xsl:param name="name-weight">bold</xsl:param>
        <xsl:param name="name"/>
        <xsl:param name="is-value-html">false</xsl:param>
        <xsl:param name="value"/>
        <xsl:call-template name="table-row-name-value">
            <xsl:with-param name="row-bottom">1px solid black</xsl:with-param>
            <xsl:with-param name="name-weight" select="$name-weight"/>
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="is-value-html" select="$is-value-html"/>
            <xsl:with-param name="value" select="$value"/>
        </xsl:call-template>
    </xsl:template>
    
    <xsl:template name="table-row-name-value">
        <xsl:param name="row-bottom">none</xsl:param>
        <xsl:param name="name-weight">bold</xsl:param>
        <xsl:param name="name"/>
        <xsl:param name="is-value-html">false</xsl:param>
        <xsl:param name="value"/>
        <fo:table-row border-bottom="{$row-bottom}">
            <fo:table-cell>
                <fo:block font-weight="{$name-weight}">
                    <xsl:value-of select="$name"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell>
                <fo:block>
                    <xsl:choose>
                        <xsl:when test="$is-value-html='true'">
                            <xsl:call-template name="xhtml-text">
                                <xsl:with-param name="root" select="$value"/>
                            </xsl:call-template>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$value"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>
    
    <xsl:template name="html-timestamp-or-specified-in-legal-text">
        <xsl:param name="value"/>
        <xsl:choose>
            <xsl:when test="not(normalize-space($value)='')">
                <![CDATA[<span>]]>
                <xsl:value-of select="$value"/>
                <![CDATA[</span>]]>
            </xsl:when>
            <xsl:otherwise>
                <![CDATA[<i>]]>
                Specified in legal text.
                <![CDATA[</i>]]>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
    <xsl:template name="html-identifier">
        <xsl:param name="value"/>
        <![CDATA[<a href="]]><xsl:value-of select="$value"/><![CDATA[">]]>
        <xsl:value-of select="$value"/>
        <![CDATA[</a>]]>
    </xsl:template>
    
    <xsl:template name="section-header">
        <xsl:param name="text"/>
        <fo:block font-weight="bold"
                  font-size="14pt"
                  line-height="17pt"
                  keep-with-next="always"
        >
            <xsl:value-of select="$text"/>
        </fo:block>
    </xsl:template>
    
    <xsl:template name="subsection-header">
        <xsl:param name="text"/>
        <fo:block font-weight="bold"
                  space-before="1em"
                  keep-with-next="always"
        >
            <xsl:value-of select="$text"/>
        </fo:block>
    </xsl:template>
    
    <xsl:template name="legal-section">
        <xsl:param name="list-style-type"/>
        <fo:list-item space-after="6pt">
            <xsl:if test="not(normalize-space(tf:Title)='')">
                <xsl:attribute name="space-before">12pt</xsl:attribute>
            </xsl:if>
            <fo:list-item-label end-indent="label-end()">
                <fo:block>
                    <xsl:choose>
                        <xsl:when test="$list-style-type='UNORDERED_CIRCLE_FILLED'">
                            <fo:inline font-size="6pt" vertical-align="0.55em">
                                &#x25CF;
                            </fo:inline>
                        </xsl:when>
                        <xsl:when test="$list-style-type='UNORDERED_CIRCLE_HOLLOW'">
                            <fo:inline font-size="6pt" vertical-align="0.55em">
                                &#x274D;
                            </fo:inline>
                        </xsl:when>
                        <xsl:when test="$list-style-type='UNORDERED_SQUARE_FILLED'">
                            <fo:inline font-size="6pt" vertical-align="0.55em">
                                &#x25A0;
                            </fo:inline>
                        </xsl:when>
                        <xsl:when test="$list-style-type='ORDERED_ARABIC_NUMERALS'">
                            <xsl:number value="number(tf:Index) + 1" format="1. "/>
                        </xsl:when>
                        <xsl:when test="$list-style-type='ORDERED_ROMAN_NUMERALS_UPPERCASE'">
                            <xsl:number value="number(tf:Index) + 1" format="I. "/>
                        </xsl:when>
                        <xsl:when test="$list-style-type='ORDERED_ROMAN_NUMERALS_LOWERCASE'">
                            <xsl:number value="number(tf:Index) + 1" format="i. "/>
                        </xsl:when>
                        <xsl:when test="$list-style-type='ORDERED_ALPHA_UPPERCASE'">
                            <xsl:number value="number(tf:Index) + 1" format="A. "/>
                        </xsl:when>
                        <xsl:when test="$list-style-type='ORDERED_ALPHA_LOWERCASE'">
                            <xsl:number value="number(tf:Index) + 1" format="a. "/>
                        </xsl:when>
                        <xsl:otherwise>
                            <!-- no bullet -->
                        </xsl:otherwise>
                    </xsl:choose>
                </fo:block>
            </fo:list-item-label>
            <fo:list-item-body start-indent="body-start()">
                <xsl:variable name="formatted-title">
                    <xsl:call-template name="xhtml-text">
                        <xsl:with-param name="root" select="tf:Title"/>
                    </xsl:call-template>
                </xsl:variable>
                <xsl:if test="not(normalize-space(string-join($formatted-title/node()))='')">
                    <fo:block font-weight="bold" space-after="6pt">
                        <xsl:copy-of select="$formatted-title"/>
                    </fo:block>
                </xsl:if>
                <fo:block>
                    <xsl:call-template name="xhtml-text">
                        <xsl:with-param name="root" select="tf:Text"/>
                    </xsl:call-template>
                </fo:block>
                <xsl:if test="count(tf:SubSections/tf:AgreementLegalSection) > 0">
                    <fo:list-block>
                        <xsl:for-each select="tf:SubSections/tf:AgreementLegalSection">
                            <xsl:sort select="tf:Index" data-type="number"/>
                            <xsl:call-template name="legal-section">
                                <xsl:with-param name="list-style-type">
                                    <xsl:value-of select="../../tf:ListStyleType"/>
                                </xsl:with-param>
                            </xsl:call-template>
                        </xsl:for-each>
                    </fo:list-block>
                </xsl:if>
            </fo:list-item-body>
        </fo:list-item>
    </xsl:template>
    
    <xsl:template name="supplemented-tip-snapshot">
        <xsl:param name="id-ref"/>
        <xsl:variable name="snapshot" select="key('supplemented-tip-snapshot-by-id', $id-ref)"/>
        <xsl:variable name="tip" select="$snapshot/tf:TrustInteroperabilityProfileSnapshot/tf:TrustInteroperabilityProfile"/>
        <fo:table table-layout="fixed">
            <fo:table-column column-width="60pt"/>
            <fo:table-column column-width="proportional-column-width(1)"/>
            <fo:table-body start-indent="0pt">
                <xsl:call-template name="table-row-name-value-line">
                    <xsl:with-param name="name">Name</xsl:with-param>
                    <xsl:with-param name="value" select="$tip/tf:Name"/>
                </xsl:call-template>
                <xsl:call-template name="table-row-name-value-line">
                    <xsl:with-param name="name">Version</xsl:with-param>
                    <xsl:with-param name="value" select="$tip/tf:Version"/>
                </xsl:call-template>
                <xsl:call-template name="table-row-name-value-line">
                    <xsl:with-param name="name">Identifier</xsl:with-param>
                    <xsl:with-param name="is-value-html">true</xsl:with-param>
                    <xsl:with-param name="value">
                        <xsl:call-template name="html-identifier">
                            <xsl:with-param name="value" select="$tip/tf:Identifier"/>
                        </xsl:call-template>
                    </xsl:with-param>
                </xsl:call-template>
            </fo:table-body>
        </fo:table>
        <fo:block font-weight="bold">
            Supplemental Legal Text
        </fo:block>
        <fo:block>
            <xsl:variable name="supplemental-legal-text" select="$snapshot/tf:SupplementalLegalText"/>
            <xsl:variable name="formatted-supplemental-legal-text">
                <xsl:call-template name="xhtml-text">
                    <xsl:with-param name="root" select="$supplemental-legal-text"/>
                </xsl:call-template>
            </xsl:variable>
            <xsl:choose>
                <xsl:when test="not(normalize-space(string-join($formatted-supplemental-legal-text/node()))='')">
                    <xsl:copy-of select="$formatted-supplemental-legal-text"/>
                </xsl:when>
                <xsl:otherwise>
                    <fo:inline font-style="italic">
                        (No supplemental legal text provided with this Trust Interoperability Profile.)
                    </fo:inline>
                </xsl:otherwise>
            </xsl:choose>
        </fo:block>
    </xsl:template>
    
    <!-- ========== Embedded XHTML Templates ========== -->
    <!-- See here: http://www.ibm.com/developerworks/library/x-xslfo2app -->
    <xsl:template name="xhtml-text">
        <xsl:param name="root"/>
        <xsl:if test="not(normalize-space($root)='')">
            <xsl:variable name="xhtml" select="normalize-space($root)"/>
            <xsl:apply-templates select="fn:parse-xml($xhtml)"/>
        </xsl:if>
    </xsl:template>
    
    <xsl:template match="p">
        <fo:block font-size="12pt" line-height="15pt" space-after="12pt">
            <xsl:apply-templates select="*|text()"/>
        </fo:block>
    </xsl:template>
    
    <xsl:template match="br">
        <fo:block> </fo:block>
    </xsl:template>
    
    <xsl:template match="span">
        <xsl:apply-templates select="*|text()"/>
    </xsl:template>
    
    <xsl:template match="b">
        <fo:inline font-weight="bold">
            <xsl:apply-templates select="*|text()"/>
        </fo:inline>
    </xsl:template>
    
    <xsl:template match="i">
        <fo:inline font-style="italic">
            <xsl:apply-templates select="*|text()"/>
        </fo:inline>
    </xsl:template>
    
    <xsl:template match="sup">
        <fo:inline vertical-align="super">
            <xsl:apply-templates select="*|text()"/>
        </fo:inline>
    </xsl:template>
    
    <xsl:template match="sub">
        <fo:inline vertical-align="sub">
            <xsl:apply-templates select="*|text()"/>
        </fo:inline>
    </xsl:template>
    
    <xsl:template match="strike">
        <fo:inline text-decoration="line-through">
            <xsl:apply-templates select="*|text()"/>
        </fo:inline>
    </xsl:template>
    
    <xsl:template match="a">
        <xsl:choose>
            <xsl:when test="@name">
                <fo:block line-height="0" space-after="0pt" font-size="0pt" id="{@name}"/>
            </xsl:when>
            <xsl:when test="@href">
                <fo:basic-link color="blue">
                    <xsl:choose>
                        <xsl:when test="starts-with(@href, '#')">
                            <xsl:attribute name="internal-destination">
                                <xsl:value-of select="substring(@href, 2)"/>
                            </xsl:attribute>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:attribute name="external-destination">
                                <xsl:value-of select="@href"/>
                            </xsl:attribute>
                        </xsl:otherwise>
                    </xsl:choose>
                    <xsl:apply-templates select="*|text()"/>
                </fo:basic-link>
                <xsl:if test="starts-with(@href, '#')">
                    <xsl:text> on page </xsl:text>
                    <fo:page-number-citation ref-id="{substring(@href, 2)}"/>
                </xsl:if>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    
    <xsl:template match="ol">
        <fo:list-block space-after="12pt">
            <xsl:for-each select="./li">
                <fo:list-item>
                    <fo:list-item-label end-indent="label-end()">
                        <fo:block>
                            <xsl:number value="position()" format="1. "/>
                        </fo:block>
                    </fo:list-item-label>
                    <fo:list-item-body start-indent="body-start()">
                        <fo:block>
                            <xsl:apply-templates select="*|text()"/>
                        </fo:block>
                    </fo:list-item-body>
                </fo:list-item>
            </xsl:for-each>
        </fo:list-block>
    </xsl:template>
    
    <xsl:template match="ul">
        <fo:list-block space-after="12pt">
            <xsl:for-each select="./li">
                <fo:list-item>
                    <fo:list-item-label end-indent="label-end()">
                        <fo:block>
                            <fo:inline font-size="6pt" vertical-align="0.55em">
                                &#x25CF;
                            </fo:inline>
                        </fo:block>
                    </fo:list-item-label>
                    <fo:list-item-body start-indent="body-start()">
                        <fo:block>
                            <xsl:apply-templates select="*|text()"/>
                        </fo:block>
                    </fo:list-item-body>
                </fo:list-item>
            </xsl:for-each>
        </fo:list-block>
    </xsl:template>
    
</xsl:stylesheet>
