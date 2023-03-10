package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.MultiTrustmarkDefinitionBuilder;
import edu.gatech.gtri.trustmark.v1_0.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.*;

/**
 * Created by brad on 3/17/16.
 */
public class ExcelMultiTdBuilder implements MultiTrustmarkDefinitionBuilder {
    //==================================================================================================================
    //  Static Variables
    //==================================================================================================================
    private static final Logger logger = LoggerFactory.getLogger(ExcelMultiTdBuilder.class);

    public static final String FONT_TD_TITLE = "tdTitleFont";

    public static final String CELL_STYLE_TD_TITLE_ROW  = "tdTitleRowCellStyle";
    public static final String CELL_STYLE_TD_TITLE      = "tdTitleCellStyle";
    public static final String CELL_STYLE_ASS_STEP_ROW  = "assStepRowCellStyle";
    public static final String CELL_STYLE_ASS_STEP_NAME = "stepNameCellStyle";
    public static final String CELL_STYLE_ASS_STEP_DESC = "stepDescCellStyle";

    public static final String LISTING_SHEET_NAME_TDS                          = "TDs";
    public static final String LISTING_SHEET_NAME_TIPS                         = "TIPs";
    public static final String TERMS_SHEET_NAME                                = "Terms";


    public static final String LISTING_COL_STEP_NAME                           = "Step Name";
    public static final String LISTING_COL_STEP_DESC                           = "Step Desc";
    public static final String LISTING_COL_CRIT_NAME                           = "Criterion Name";
    public static final String LISTING_COL_CRIT_DESC                           = "Criterion Desc";
    public static final String LISTING_COL_CITATIONS                           = "Citations";
    public static final String LISTING_COL_ARTIFACTS                           = "Artifacts";
    public static final String LISTING_COL_KEYWORDS                            = "Keywords";

    //TD
    public static final String LISTING_COL_TD_MONIKER                          = "TD Moniker";
    public static final String LISTING_COL_TD_NAME                             = "TD Name";
    public static final String LISTING_COL_TD_VERSION                          = "TD Version";
    public static final String LISTING_COL_TD_DESC                             = "TD Description";
    public static final String LISTING_COL_TD_PUB_TIME                         = "TD Publication DateTime";

    //TIP
    public static final String LISTING_COL_TIP_MONIKER                         = "TIP Moniker";
    public static final String LISTING_COL_TIP_NAME                            = "TIP Name";
    public static final String LISTING_COL_TIP_VERSION                         = "TIP Version";
    public static final String LISTING_COL_TIP_DESC                            = "TIP Description";
    public static final String LISTING_COL_TIP_PUB_TIME                        = "TIP Publication DateTime";

    public static final String LISTING_COL_CATEGORY                            = "Category";
    public static final String LISTING_COL_TRUSTMARK_PROVIDER                  = "Trustmark Provider";
    public static final String LISTING_COL_TRUST_EXPRESSION                    = "Trust Expression";
    public static final String LISTING_COL_PRIMARY                             = "Primary";


    public static final String LISTING_COL_STAKEHOLDER_DESC                    = "Stakeholder Desc";
    public static final String LISTING_COL_RECIPIENT_DESC                      = "Recipient Desc";
    public static final String LISTING_COL_RELYING_PARTY_DESC                  = "Relying Party Desc";
    public static final String LISTING_COL_PROVIDER_DESC                       = "Provider Desc";
    public static final String LISTING_COL_PROVIDER_ELIGIBILITY                = "Provider Eligibility Criteria";
    public static final String LISTING_COL_ASSESSOR_QUALIFICATIONS_DESC        = "Assessor Qualifications Desc";
    public static final String LISTING_COL_REVOCATION_CRITERIA                 = "Trustmark Revocation Criteria";
    public static final String LISTING_COL_EXTENSION_DESC                      = "Extension Description";
    public static final String LISTING_COL_ISSUANCE_CRITERIA                   = "Issuance Criteria";
    public static final String LISTING_COL_CRITERIA_PREFACE                    = "Criteria Preface";
    public static final String LISTING_COL_STEP_PREFACE                        = "Assessment Step Preface";
    public static final String LISTING_COL_ASSESSMENT_PREFACE                  = "Assessment Preface";
    public static final String LISTING_COL_TIPS                                = "TIPs";
    public static final String LISTING_COL_DEPRECATED                          = "Deprecated";
    public static final String LISTING_COL_SUPERSEDES                          = "Supersedes";
    public static final String LISTING_COL_SUPERSEDED_BY                       = "Superseded By";
    public static final String LISTING_COL_NOTES                               = "Notes";
    public static final String LISTING_COL_LEGAL_NOTICE                        = "Legal Notice";

    public static final String TERMS_COL_TERMS                                 = "Terms";
    public static final String LISTING_TERMS_INCLUDE                           = "Terms Include";
    public static final String LISTING_TERMS_EXCLUDE                           = "Terms Exclude";

    public static final String TERMS_COL_TERM                                  = "Term";
    public static final String TERMS_COL_TERM_NAME                             = "Term Name";
    public static final String TERMS_COL_TERM_DEFINITION                       = "Term Definition";
    public static final String TERMS_COL_SOURCES                               = "Sources";
    public static final String TERMS_COL_ABBREVIATIONS                         = "Abbreviations";
    public static final String TERMS_COL_DEFINITION                            = "Definition";
    public static final String TERMS_COL_PARAMETERS                            = "Parameters";

    public static final String[] TERMS_COLUMNS = new String[]{      TERMS_COL_TERM,
                                                                    TERMS_COL_ABBREVIATIONS,
                                                                    TERMS_COL_DEFINITION};

    public static final String[] LISTING_COLUMNS = new String[]{    LISTING_COL_STEP_NAME,
                                                                    LISTING_COL_STEP_DESC,
                                                                    LISTING_COL_CRIT_NAME,
                                                                    LISTING_COL_CRIT_DESC,
                                                                    LISTING_COL_CITATIONS,
                                                                    LISTING_COL_ARTIFACTS,
                                                                    LISTING_COL_KEYWORDS,
                                                                    LISTING_COL_TD_MONIKER,
                                                                    LISTING_COL_TD_NAME,
                                                                    LISTING_COL_TD_VERSION,
                                                                    LISTING_COL_TD_DESC,
                                                                    LISTING_COL_TD_PUB_TIME,
                                                                    LISTING_COL_STAKEHOLDER_DESC,
                                                                    LISTING_COL_RECIPIENT_DESC,
                                                                    LISTING_COL_RELYING_PARTY_DESC,
                                                                    LISTING_COL_PROVIDER_DESC,
                                                                    LISTING_COL_PROVIDER_ELIGIBILITY,
                                                                    LISTING_COL_ASSESSOR_QUALIFICATIONS_DESC,
                                                                    LISTING_COL_REVOCATION_CRITERIA,
                                                                    LISTING_COL_EXTENSION_DESC,
                                                                    LISTING_COL_ISSUANCE_CRITERIA,
                                                                    LISTING_COL_CRITERIA_PREFACE,
                                                                    LISTING_COL_STEP_PREFACE,
                                                                    LISTING_COL_DEPRECATED,
                                                                    LISTING_COL_SUPERSEDES,
                                                                    LISTING_COL_SUPERSEDED_BY,
                                                                    LISTING_COL_NOTES,
                                                                    LISTING_COL_LEGAL_NOTICE};

    public static final Map<String, Integer> LISTING_COLUMN_POSITIONS = new HashMap<>();
    public static final Map<String, Integer> TERMS_COLUMN_POSITIONS = new HashMap<>();

    public static final Comparator<Term> TERM_COMPARATOR = new Comparator<Term>() {
        @Override
        public int compare(Term o1, Term o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    };

    static  {
        for( int i = 0; i < LISTING_COLUMNS.length; i++ ){
            LISTING_COLUMN_POSITIONS.put(LISTING_COLUMNS[i], i);
        }
        for( int i = 0; i < TERMS_COLUMNS.length; i++ ){
            TERMS_COLUMN_POSITIONS.put(TERMS_COLUMNS[i], i);
        }
    }
    //==================================================================================================================
    //  Instance Variables
    //==================================================================================================================
    private File outputFile;
    private HSSFWorkbook workbook;
    private Map<String, HSSFFont> fonts;
    private Map<String, HSSFCellStyle> cellStyles;
    private Sheet tdListSheet;
    private int listingSheetIndex;
    // We collect unique terms as we go for the terms sheet at the end.
    private List<Term> termsList;

    private TrustmarkDefinition lastTd;
    private AssessmentStep lastAssStep;
    //==================================================================================================================
    // Getters
    //==================================================================================================================
    public File getOutputFile() {
        return outputFile;
    }
    //==================================================================================================================
    // Constructors
    //==================================================================================================================
    private ExcelMultiTdBuilder(){} // Can't instantiate this one without a file.
    public ExcelMultiTdBuilder(File outputFile){
        this.outputFile = outputFile;
    }
    public ExcelMultiTdBuilder(String outputFilePath){
        this.outputFile = new File(outputFilePath);
    }

    //==================================================================================================================
    // Interface Implementation
    //==================================================================================================================
    @Override
    public void init() {
        this.workbook = new HSSFWorkbook();
        this.fonts = createFonts(this.workbook);
        this.cellStyles = createCellStyles(this.workbook, this.fonts);
        this.tdListSheet = workbook.createSheet(LISTING_SHEET_NAME_TDS);
        this.initializeFirstSheet();
        this.listingSheetIndex = 1;
        lastTd = null;
        lastAssStep = null;
    }

    @Override
    public Object finish() {
        this.finalizeTermsSheet();

        if( this.outputFile.exists() ) // TODO Maybe write it to backup file first?
            this.outputFile.delete();

        try {
            FileOutputStream fout = new FileOutputStream(this.outputFile);
            this.workbook.write(fout);
            fout.flush();
            fout.close();
            logger.debug("Successfully wrote file: " + this.outputFile.getCanonicalPath());
        }catch(Throwable t){
            throw new UnsupportedOperationException("Not able to write out workbook to file!", t);
        }

        return this.outputFile;
    }

    @Override
    public void add(TrustmarkDefinition td) {
        logger.debug("Adding trustmark definition: "+td.getMetadata().getIdentifier().toString());
        for(AssessmentStep assStep : td.getAssessmentSteps() ){
            writeNextRow(assStep, td);
        }
        appendUniqueTerms(td.getTerms());
    }//end add(TrustmarkDefinition)

    @Override
    public void addAll(List<TrustmarkDefinition> tds) {
        if( tds != null && !tds.isEmpty() ) {
            for (TrustmarkDefinition td : tds) {
                this.add(td);
            }
        }
    }
    //==================================================================================================================
    // Helper Methods
    //==================================================================================================================
    private void finalizeTermsSheet(){
        Collections.sort(this.termsList, TERM_COMPARATOR);
        Sheet termsSheet = this.workbook.createSheet(TERMS_SHEET_NAME);
        Row headerRow = termsSheet.createRow(0);
        headerRow.createCell(TERMS_COLUMN_POSITIONS.get(TERMS_COL_TERM)).setCellValue(TERMS_COL_TERM);
        headerRow.createCell(TERMS_COLUMN_POSITIONS.get(TERMS_COL_ABBREVIATIONS)).setCellValue(TERMS_COL_ABBREVIATIONS);
        headerRow.createCell(TERMS_COLUMN_POSITIONS.get(TERMS_COL_DEFINITION)).setCellValue(TERMS_COL_DEFINITION);

        int termRow = 1;
        for( Term term : this.termsList ){
            Row row = termsSheet.createRow(termRow++);
            row.createCell(TERMS_COLUMN_POSITIONS.get(TERMS_COL_TERM)).setCellValue(term.getName());
            row.createCell(TERMS_COLUMN_POSITIONS.get(TERMS_COL_ABBREVIATIONS)).setCellValue(toPipeFormat(term.getAbbreviations()));
            row.createCell(TERMS_COLUMN_POSITIONS.get(TERMS_COL_DEFINITION)).setCellValue(term.getDefinition());
        }
    }

    private void writeNextRow(AssessmentStep step, TrustmarkDefinition td){
        Row tdRow = this.tdListSheet.createRow(this.listingSheetIndex++);
        HSSFCellStyle defaultStyle = this.cellStyles.get(CELL_STYLE_ASS_STEP_ROW);
        HSSFCellStyle nameStyle = this.cellStyles.get(CELL_STYLE_ASS_STEP_NAME);
        HSSFCellStyle descStyle = this.cellStyles.get(CELL_STYLE_ASS_STEP_DESC);
        tdRow.setRowStyle(defaultStyle);
        for(ConformanceCriterion crit : step.getConformanceCriteria()){
            insertCell(tdRow, nameStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_STEP_NAME), step.getName());
            insertCell(tdRow, descStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_STEP_DESC), step.getDescription());
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_CRIT_NAME), crit.getName());
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_CRIT_DESC), crit.getDescription());
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_CITATIONS), toPipeColonFormat(citationsToMap(crit.getCitations())) );
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_ARTIFACTS), toPipeColonFormat(artifactsToMap(step.getArtifacts())) );
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_KEYWORDS), toPipeFormat(td.getMetadata().getKeywords()));

            //SUPERSEDES
            List<String> idsSupersedes = new ArrayList<>();
            if( td.getMetadata().getSupersedes() != null && td.getMetadata().getSupersedes().size() > 0 ) {
                for( TrustmarkFrameworkIdentifiedObject tfi : td.getMetadata().getSupersedes() ){
                    idsSupersedes.add(tfi.getIdentifier().toString());
                }
            }
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_SUPERSEDES), toPipeFormat(idsSupersedes));

            //SUPERSEDED_BY
            List<String> idsSupersededBy = new ArrayList<>();
            if( td.getMetadata().getSupersededBy() != null && td.getMetadata().getSupersededBy().size() > 0 ) {
                for( TrustmarkFrameworkIdentifiedObject tfi : td.getMetadata().getSupersededBy() ){
                    idsSupersededBy.add(tfi.getIdentifier().toString());
                }
            }
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_SUPERSEDED_BY), toPipeFormat(idsSupersededBy));

            //DEPRECATED
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_DEPRECATED), td.getMetadata().isDeprecated());

            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_CRITERIA_PREFACE), td.getConformanceCriteriaPreface());
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_STEP_PREFACE), td.getAssessmentStepPreface());

            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_ISSUANCE_CRITERIA), td.getIssuanceCriteria());

            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_TD_MONIKER), getMoniker(td.getMetadata().getIdentifier()));
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_TD_NAME), td.getMetadata().getName());
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_TD_VERSION), td.getMetadata().getVersion());
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_TD_DESC), td.getMetadata().getDescription());
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_TD_PUB_TIME), td.getMetadata().getPublicationDateTime());

            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_STAKEHOLDER_DESC), td.getMetadata().getTargetStakeholderDescription());
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_RECIPIENT_DESC), td.getMetadata().getTargetRecipientDescription());
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_RELYING_PARTY_DESC), td.getMetadata().getTargetRelyingPartyDescription());
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_PROVIDER_DESC), td.getMetadata().getTargetProviderDescription());
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_PROVIDER_ELIGIBILITY), td.getMetadata().getProviderEligibilityCriteria());
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_ASSESSOR_QUALIFICATIONS_DESC), td.getMetadata().getAssessorQualificationsDescription());
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_REVOCATION_CRITERIA), td.getMetadata().getTrustmarkRevocationCriteria());
            insertCell(tdRow, defaultStyle, LISTING_COLUMN_POSITIONS.get(LISTING_COL_EXTENSION_DESC), td.getMetadata().getExtensionDescription());
        }

    }

    private void appendUniqueTerms(Collection<Term> terms){
        if( this.termsList == null )
            this.termsList = new ArrayList<>();
        if( terms != null && !terms.isEmpty() ){
            for( Term term : terms ){
                if( !this.termsList.contains(term) )
                    this.termsList.add(term);
            }
        }
    }

    private String toPipeFormat(Collection things){
        if( things == null || things.isEmpty() )
            return "";
        StringBuilder builder = new StringBuilder();
        Iterator<Object> thingIter = things.iterator();
        while( thingIter.hasNext() ){
            Object next = thingIter.next();
            // TODO Can we do better based on thing type?
            builder.append(escapeColonsAndPipes(next.toString()));
            if( thingIter.hasNext() ){
                builder.append("|");
            }
        }
        return builder.toString();
    }//end toPipeFormat()

    private String toPipeColonFormat(Map<String, String> input){
        StringBuilder builder = new StringBuilder();
        Iterator<String> keysIter = input.keySet().iterator();
        while( keysIter.hasNext() ){
            String nextKey = keysIter.next();
            builder.append(escapeColonsAndPipes(nextKey)).append(":").append(escapeColonsAndPipes(input.get(nextKey)));
            if( keysIter.hasNext() )
                builder.append("|");
        }
        return builder.toString();
    }//end toPipeColonFormat()

    private String escapeColonsAndPipes(String val){
        String newVal = val.replace(":", "\\:");
        newVal = newVal.replace("|", "\\|");
        return newVal;
    }//end escapeColonsAndPipes()

    private Map<String, String> artifactsToMap(Collection<Artifact> artifacts) {
        Map<String, String> mapVal = new HashMap<>();
        for( Artifact artifact : artifacts ){
            mapVal.put(artifact.getName(), artifact.getDescription());
        }
        return mapVal;
    }

    private Map<String, String> citationsToMap(Collection<Citation> citations){
        Map<String, String> citationsMap = new HashMap<>();
        for( Citation citation : citations ){
            citationsMap.put(citation.getSource().getIdentifier(), citation.getDescription());
        }
        return citationsMap;
    }

    private void insertCell(Row row, HSSFCellStyle style, int position, Object value){
        Cell cell = row.createCell(position);
        cell.setCellStyle(style);
        if( value != null ){
            if( String.class.equals(value.getClass()) ){
                cell.setCellValue((String) value);
            }else if( Boolean.class.equals(value.getClass()) ){
                cell.setCellValue((boolean) value);
            }else if( Calendar.class.equals(value.getClass()) ){
                cell.setCellValue((Calendar) value);
            }else if( Date.class.equals(value.getClass()) ){
                cell.setCellValue((Date) value);
            }else if( Number.class.equals(value.getClass()) ){
                cell.setCellValue(((Number) value).doubleValue());
            }else{
                cell.setCellValue(value.toString());
            }
        }else{
            cell.setCellValue("");
        }
        // TODO Can we do better with the value based on type?
        if( value != null && value.toString().trim().length() > 0 ) {
            cell.setCellValue(value.toString());
        }else{
            cell.setCellValue("");
        }
    }

    private String getMoniker(URI uri) {
        // TODO Can we do better?
        return uri.toString();
    }

    private void initializeFirstSheet(){
        for( int i = 0; i < 20; i++ ) {
            this.tdListSheet.setColumnWidth(i, 15000);
        }
        Row headerRow = this.tdListSheet.createRow(0);
        headerRow.setRowStyle(this.cellStyles.get(CELL_STYLE_TD_TITLE_ROW));
        List headerList = Arrays.asList(LISTING_COLUMNS);
        insertListToRow(headerRow, this.cellStyles.get(CELL_STYLE_TD_TITLE_ROW), 0, headerList);
    }//end initializeFirstSheet()

    private void insertListToRow(Row row, HSSFCellStyle style, int startIndex, List values){
        int curIndex = startIndex;
        for( Object value : values ){
            insertCell(row, style, curIndex++, value);
        }
    }//end insertListToRow()

    private Map<String, HSSFFont> createFonts(HSSFWorkbook workbook){
        Map<String, HSSFFont> fonts = new HashMap<>();
        HSSFFont tdTitleFont = workbook.createFont();
        tdTitleFont.setBold(true);
        tdTitleFont.setFontHeightInPoints((short) 20);
        tdTitleFont.setFontName(HSSFFont.FONT_ARIAL);
        fonts.put(FONT_TD_TITLE, tdTitleFont);
        return fonts;
    }//end createFonts()

    private Map<String, HSSFCellStyle> createCellStyles(HSSFWorkbook workbook, Map<String, HSSFFont> fonts){
        Map<String, HSSFCellStyle> cellStyles = new HashMap<>();

        HSSFCellStyle tdTitleRowCellStyle = workbook.createCellStyle();
        tdTitleRowCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        tdTitleRowCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
        tdTitleRowCellStyle.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
        tdTitleRowCellStyle.setWrapText(true);
        cellStyles.put(CELL_STYLE_TD_TITLE_ROW, tdTitleRowCellStyle);

        HSSFCellStyle tdTitleCellStyle = workbook.createCellStyle();
        tdTitleCellStyle.setWrapText(true);
        tdTitleCellStyle.setFont(fonts.get(FONT_TD_TITLE));
        cellStyles.put(CELL_STYLE_TD_TITLE, tdTitleRowCellStyle);

        HSSFCellStyle assStepRowCellStyle = workbook.createCellStyle();
        assStepRowCellStyle.setWrapText(true);
//        assStepRowCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        assStepRowCellStyle.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
//        assStepRowCellStyle.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());
        cellStyles.put(CELL_STYLE_ASS_STEP_ROW, assStepRowCellStyle);

        HSSFCellStyle stepNameCellStyle = workbook.createCellStyle();
        stepNameCellStyle.setWrapText(true);
        cellStyles.put(CELL_STYLE_ASS_STEP_NAME, stepNameCellStyle);

        HSSFCellStyle stepDescCellStyle = workbook.createCellStyle();
        stepDescCellStyle.setWrapText(true);
        cellStyles.put(CELL_STYLE_ASS_STEP_DESC, stepDescCellStyle);

        return cellStyles;
    }//end


}//end ExcelMultiTdBuilder
