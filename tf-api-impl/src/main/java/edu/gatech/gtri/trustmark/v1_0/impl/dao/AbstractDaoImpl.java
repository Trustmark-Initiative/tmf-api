package edu.gatech.gtri.trustmark.v1_0.impl.dao;

import com.j256.ormlite.dao.Dao;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.*;

/**
 * TODO: Write a description here
 *
 * @user brad
 * @date 9/15/16
 */
public abstract class AbstractDaoImpl {
    //====================================================================================================================
    //  STATIC VARIABLES
    //====================================================================================================================
    private static final Logger log = LogManager.getLogger(AbstractDaoImpl.class);
    public static List<String> SIMPLE_WORDS = new ArrayList<>();
    /**
     * Copied from: www.link-assistant.com/seo-stop-words.html
     */
    public static String STOP_WORDS_SOURCE = "a able about above abroad according accordingly across actually adj after afterwards again against ago ahead ain't all allow allows almost alone along alongside already also although always am amid amidst among amongst an and another any anybody anyhow anyone anything anyway anyways anywhere apart appear appreciate appropriate are aren't around as a's aside ask asking associated at available away awfully b back backward backwards be became because become becomes becoming been before beforehand begin behind being believe below beside besides best better between beyond both brief but by c came can cannot cant can't caption cause causes certain certainly changes clearly c'mon co co.  com come comes concerning consequently consider considering contain containing contains corresponding could couldn't course c's currently d dare daren't definitely described despite did didn't different directly do does doesn't doing done don't down downwards during e each edu eg eight eighty either else elsewhere end ending enough entirely especially et etc even ever evermore every everybody everyone everything everywhere ex exactly example except f fairly far farther few fewer fifth first five followed following follows for forever former formerly forth forward found four from further furthermore g get gets getting given gives go goes going gone got gotten greetings h had hadn't half happens hardly has hasn't have haven't having he he'd he'll hello help \t hence her here hereafter hereby herein here's hereupon hers herself he's hi him himself his hither hopefully how howbeit however hundred i i'd ie if ignored i'll i'm immediate in inasmuch inc inc.  indeed indicate indicated indicates inner inside insofar instead into inward is isn't it it'd it'll its it's itself i've j just k keep keeps kept know known knows l last lately later latter latterly least less lest let let's like liked likely likewise little look looking looks low lower ltd m made mainly make makes many may maybe mayn't me mean meantime meanwhile merely might mightn't mine minus miss more moreover most mostly mr mrs much must mustn't my myself n name namely nd near nearly necessary need needn't needs neither never neverf neverless nevertheless new next nine ninety no nobody non none nonetheless noone no-one nor normally not nothing notwithstanding novel now nowhere o obviously of off often oh ok okay old on once one ones one's only onto opposite or other others otherwise ought oughtn't our ours ourselves out outside over overall own p particular particularly past per perhaps placed please plus possible presumably probably provided provides q que quite qv r rather rd re really reasonably recent recently regarding regardless regards relatively respectively right round s said same saw say saying says second secondly \t see seeing seem seemed seeming seems seen self selves sensible sent serious seriously seven several shall shan't she she'd she'll she's should shouldn't since six so some somebody someday somehow someone something sometime sometimes somewhat somewhere soon sorry specified specify specifying still sub such sup sure t take taken taking tell tends th than thank thanks thanx that that'll thats that's that've the their theirs them themselves then thence there thereafter thereby there'd therefore therein there'll there're theres there's thereupon there've these they they'd they'll they're they've thing things think third thirty this thorough thoroughly those though three through throughout thru thus till to together too took toward towards tried tries truly try trying t's twice two u un under underneath undoing unfortunately unless unlike unlikely until unto up upon upwards us use used useful uses using usually v value various versus very via viz vs w want wants was wasn't way we we'd welcome well we'll went were we're weren't we've what whatever what'll what's what've when whence whenever where whereafter whereas whereby wherein where's whereupon wherever whether which whichever while whilst whither who who'd whoever whole who'll whom whomever who's whose why will willing wish with within without wonder won't would wouldn't x y yes yet you you'd you'll your you're yours yourself yourselves you've z zero";

    static {
        SIMPLE_WORDS.addAll(Arrays.asList(STOP_WORDS_SOURCE.split("\\s+")));
    }
    //====================================================================================================================
    //  HELPER METHODS
    //====================================================================================================================
    protected Object getSingleObject(Dao dao, String fieldName, Object value) throws SQLException {
        List cacheObjs = dao.queryForEq(fieldName, value);
        if( cacheObjs != null && cacheObjs.size() > 0 ){
            return cacheObjs.get(0);
        }else{
            log.debug("Could not find anything with "+fieldName+" = "+value);
            return null;
        }
    }



    /**
     * Given a search text string, this method will split into each individual search term.  Note that it separates exact
     * search matches (those given in double quotes) and relaxed search terms (those terms just there).  The result is
     * a Map with exact -> list of terms and regular -> list of relaxed terms.  Either or both can be empty, but not null.
     */
    public static Map<String, List<String>> parseTerms(String searchText){
        List<String> exactTerms = new ArrayList<>();
        List<String> terms = new ArrayList<>();

        // First, we pull out exact phrases surrounded by double quotes.
        StringWriter sw = new StringWriter();
        StringWriter exactPhrase = new StringWriter();
        boolean quotesOn = false;
        for( int i = 0; i < searchText.length(); i++ ){
            char c = searchText.charAt(i);
            if( c == '"' ){
                if( quotesOn ) {
                    exactTerms.add(exactPhrase.toString());
                    quotesOn = false;
                    exactPhrase = new StringWriter();
                }else{
                    quotesOn = true;
                }
            }else{
                if( quotesOn ){
                    exactPhrase.append(c);
                }else{
                    sw.append(c);
                }
            }
        }

        String restAfterQuotesRemoved = sw.toString().toLowerCase();
        if( restAfterQuotesRemoved.trim().length() > 0 ) {
            String[] parts = restAfterQuotesRemoved.split("\\s+");
            if( parts != null && parts.length > 0 ){
                for( int j = 0; j < parts.length; j++ ){
                    String part = parts[j].trim();
                    if( part.length() > 0 && isNotSimple(part) ){
                        terms.add(part);
                    }
                }
            }
        }

        HashMap termsMap = new HashMap();
        termsMap.put("exact", exactTerms);
        termsMap.put("regular", terms);
        return termsMap;
    }

    /**
     * Returns true if a word is complex, and should be included as a search term.  Returns false if it is a simple
     * helper word, like a, an or the.
     */
    public static boolean isNotSimple(String word){
        return !SIMPLE_WORDS.contains(word);
    }

    public static boolean isSimple(String word){
        return SIMPLE_WORDS.contains(word);
    }

}/* end AbstractDaoImpl */
