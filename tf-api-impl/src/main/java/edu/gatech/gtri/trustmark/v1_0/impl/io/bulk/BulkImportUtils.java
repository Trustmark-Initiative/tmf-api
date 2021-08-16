package edu.gatech.gtri.trustmark.v1_0.impl.io.bulk;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.dom4j.CharacterData;
import org.dom4j.Element;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nicholas on 9/6/2016.
 */
public class BulkImportUtils {

    private static final Logger log = LogManager.getLogger(BulkImportUtils.class);

    private BulkImportUtils() {
        // nothing here right now -- this class is a collection of static methods
    }
    
    
    /////////////////////////
    // String Modification //
    /////////////////////////
    
    /**
     * Generates a moniker from the given name.  Monikers are "URL Safe" representations of the name.
     */
    public static String generateMoniker(String name) {
        String moniker = name;
        moniker = moniker.toLowerCase();
        moniker = moniker.replaceAll("\\s+", "-"); // Replace any whitespace with a single dash
        return moniker;
    }//end generateMoniker()
    
    /**
     * Given something that someone thinks is a valid moniker, this function replaces it with something that actually is.
     */
    public static String cleanseMoniker(String moniker) {
        String newMoniker = StringUtils.isNotBlank(moniker) ? moniker : "";
        newMoniker = newMoniker.toLowerCase(); // Make sure it's consistent accross the board.
        StringBuilder monikerBuilder = new StringBuilder();
        for( int i = 0; i < newMoniker.length(); i++ ){
            if( isValidMonikerCharacter(newMoniker.charAt(i)) ){
                monikerBuilder.append(newMoniker.charAt(i));
            }else{
                monikerBuilder.append("_"); // Instead of removing the character, we underscore it.
            }
        }
        return monikerBuilder.toString();
    }//end cleanseMoniker()

    /**
     * Returns true if you can use this character in a moniker, and false if you cannot.
     */
    public static boolean isValidMonikerCharacter(Character c){
        boolean canUse = false;
        if( Character.isAlphabetic(c) || Character.isDigit(c) ){
            canUse = true;
        }else if( c == '.' || c == '-' || c == '_' ){
            canUse = true;
        }
        return canUse;
    }

    public static boolean isValidUri(String url)
    {
        try
        {
            URI newUri = new URI(url);
            return true;
        } catch(URISyntaxException me)  {
            return false;
        }
    }
    /**
     * Converts the given name into a decent identifier.
     */
    public static String idFromName(String name) {
        if (StringUtils.isNotBlank(name)) {
            String id = name;
            id = id.replaceAll("[^A-Za-z0-9-]", "");
            return id;
        } else {
            return newUniqueId(null);
        }
    }
    
    /**
     * Generates a new unique id.  Should be good for heat death of universe, based on Java UUID.
     */
    public static String newUniqueId(String name) {
        String id = null;
        if (StringUtils.isNotBlank(name)) {
            id = idFromName(name);
        }
        if (id == null)
            id = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        if (Character.isDigit(id.charAt(0)))
            id = "_" + id;
        return id;
    }
    
    /**
     * Generates a unique variable name for use in expressions.
     */
    public static String getUniqueVariableName(
        String prefix,
        String name,
        Pattern blacklistPattern,
        int maxLength,
        Set<String> existingNames
    ) {
        String targetName = blacklistPattern.matcher(name).replaceAll("");

        if (targetName.length() > maxLength) {
            targetName = targetName.substring(0, maxLength);
        }

        String result = prefix + targetName;
        if (existingNames.contains(result)) {
            String baseName = result;
            int suffix = 1;
            do {
                ++suffix;
                result = baseName + suffix;
            } while (existingNames.contains(result));
        }
        return result;
    }
    
    /**
     * Filters the incoming human-readable string for character set issues.
     */
    public static String filterHumanReadableText(String originalText) {
        originalText = defaultTrim(originalText, "");
        StringBuilder sb = new StringBuilder();
        int length = originalText.length();
        for (int i = 0; i < length; ++i) {
            char c = originalText.charAt(i);
            if (c < 0x80) {
                // normal ASCII text
                sb.append(c);
            }
            else if (c == '\u00a0') { sb.append(' '); }   // http://www.charbase.com/00a0-unicode-no-break-space
            else if (c == '\u2013') { sb.append('-'); }   // http://www.charbase.com/2013-unicode-en-dash
            else if (c == '\u2014') { sb.append("--"); }  // http://www.charbase.com/2014-unicode-em-dash
            else if (c == '\u2015') { sb.append("---"); } // http://www.charbase.com/2015-unicode-horizontal-bar
            else if (c == '\u2018') { sb.append('\''); }  // http://www.charbase.com/2018-unicode-left-single-quotation-mark
            else if (c == '\u2019') { sb.append('\''); }  // http://www.charbase.com/2019-unicode-right-single-quotation-mark
            else if (c == '\u201c') { sb.append('"'); }   // http://www.charbase.com/201c-unicode-left-double-quotation-mark
            else if (c == '\u201d') { sb.append('"'); }   // http://www.charbase.com/201d-unicode-right-double-quotation-mark
            else if (c == '\u2026') { sb.append("..."); } // http://www.charbase.com/2026-unicode-horizontal-ellipsis
            else if( containsChar(ALLOWED_CHARS, c) ) {sb.append(c);} // Allow section break
            else{
                log.warn("Removing undesirable character '"+c+"' from output string");
            }
        }
        return sb.toString();
    }

    protected static boolean containsChar(String s, char c ){
        for( int i = 0; i < s.length(); i++ ){
            if( s.charAt(i) == c ){
                return true;
            }
        }
        return false;
    }

    /**
     * This string contains the 'valid' characters which are allowed when filtering "Human-Readable" latin based text.
     * This list comes from the most common Unicode characters list, found in github here:
     *     https://gist.github.com/ivandrofly/0fe20773bd712b303f78
     * Which was recommended by searching google (first result):
     *     https://www.google.com/search?q=most+common+unicode+characters
     */
    public static String ALLOWED_CHARS =
            "¡¢£¤¥¦§¨©ª«¬ ®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿŁłŃńŅņŇňŊŋ"+
            "ŌōŎŏŐőŒœŔŕŖŗŘřŚśŜŝŞşŠšŢţŤťŦŧŨũŪūŬŭŮůŰűŴŵŶŷŸŹźŻżŽžſƆƎƜɐɑɒɔɘəɛɜɞɟɡɢɣɤɥɨɪɬɮɯɰɴɵɶɷɸɹʁʇʌʍʎʞΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤ"+
            "ΥΦΧΨΩαβγδεζηθικλμνξοπρςστυφχψωАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюяᴀᴁᴂᴃᴄᴅᴆᴇᴈᴉᴊᴋ"+
            "ᴌᴍᴎᴏᴐᴑᴒᴓᴔᴕᴖᴗᴘᴙᴚᴛᴜᴝᴞᴟᴠᴡᴢᴣᴤᴥᴦᴧᴨᴩᴪẞỲỳỴỵỸỹ‐‑‒–—―‖‗‘’‚‛“”„‟†‡•‣․‥…‧‰‱′″‴‵‶‷‸‹›※‼‽‾‿⁀⁁⁂⁃⁄⁅⁆⁇⁈⁉⁊⁋⁌⁍⁎⁏⁐⁑⁒⁓⁔⁕⁗"+
            "⁰ⁱ⁴⁵⁶⁷⁸⁹⁺⁻⁼⁽⁾ⁿ₀₁₂₃₄₅₆₇₈₉₊₋₌₍₎₠₡₢₣₤₥₦₧₨₩₪₫€₭₮₯₰₱₲₳₴₵₶₷₸₹℀℁ℂ℃℄℅℆ℇ℈℉ℊℋℌℍℎℏℐℑℒℓ℔ℕ№℗℘ℙℚℛℜℝ℞℟℠℡™℣ℤ℥Ω℧ℨ℩"+
            "KÅℬℭ℮ℯℰℱℲℳℴℵℶℷℸ⅁⅂⅃⅄ⅅⅆⅇⅈⅉ⅋ⅎ⅐⅑⅒⅓⅔⅕⅖⅗⅘⅙⅚⅛⅜⅝⅞⅟ⅠⅡⅢⅣⅤⅥⅦⅧⅨⅩⅪⅫⅬⅭⅮⅯⅰⅱⅲⅳⅴⅵⅶⅷⅸⅹⅺⅻⅼⅽⅾⅿↄ←↑→↓↔↕↖↗↘↙↚↛↜↝↞↟↠"+
            "↡↢↣↤↥↦↧↨↩↪↫↬↭↮↯↰↱↲↳↴↵↶↷↸↹↺↻↼↽↾↿⇀⇁⇂⇃⇄⇅⇆⇇⇈⇉⇊⇋⇌⇍⇎⇏⇐⇑⇒⇓⇔⇕⇖⇗⇘⇙⇚⇛⇜⇝⇞⇟⇠⇡⇢⇣⇤⇥⇦⇧⇨⇩⇪⇫⇬⇭⇮⇯⇰⇱⇲⇳⇴⇵⇶⇷⇸⇹⇺⇻⇼⇽⇾⇿∀∁∂∃∄∅∆∇∈∉"+
            "∊∋∌∍∎∏∐∑−∓∔∕∖∗∘∙√∛∜∝∞∟∠∡∢∣∤∥∦∧∨∩∪∫∬∭∮∯∰∱∲∳∴∵∶∷∸∹∺∻∼∽∾∿≀≁≂≃≄≅≆≇≈≉≊≋≌≍≎≏≐≑≒≓≔≕≖≗≘≙≚≛≜≝≞≟≠≡≢≣≤≥≦≧≨≩≪≫≬≭≮≯"+
            "≰≱≲≳≴≵≶≷≸≹≺≻≼≽≾≿⊀⊁⊂⊃⊄⊅⊆⊇⊈⊉⊊⊋⊌⊍⊎⊏⊐⊑⊒⊓⊔⊕⊖⊗⊘⊙⊚⊛⊜⊝⊞⊟⊠⊡⊢⊣⊤⊥⊦⊧⊨⊩⊪⊫⊬⊭⊮⊯⊰⊱⊲⊳⊴⊵⊶⊷⊸⊹⊺⊻⊼⊽⊾⊿⋀⋁⋂⋃⋄⋅⋆⋇⋈⋉⋊⋋"+
            "⋌⋍⋎⋏⋐⋑⋒⋓⋔⋕⋖⋗⋘⋙⋚⋛⋜⋝⋞⋟⋠⋡⋢⋣⋤⋥⋦⋧⋨⋩⋪⋫⋬⋭⋮⋯⋰⋱⌀⌁⌂⌃⌄⌅⌆⌇⌈⌉⌊⌋⌐⌑⌒⌓⌔⌕⌖⌗⌘⌙⌚⌛⌠⌡⌢⌣⌤⌥⌦⌧⌨⌫⌬⎛⎜⎝⎞⎟⎠⎡⎢⎣⎤⎥⎦⎧⎨⎩⎪⎫⎬⎭"+
            "⏎⏏⏚⏛⏰⏱⏲⏳␢␣─━│┃┄┅┆┇┈┉┊┋┌┍┎┏┐┑┒┓└┕┖┗┘┙┚┛├┝┞┟┠┡┢┣┤┥┦┧┨┩┪┫┬┭┮┯┰┱┲┳┴┵┶┷┸┹┺┻┼┽┾┿╀╁╂╃╄╅╆╇╈╉╊╋╌╍╎╏═║╒╓╔╕╖╗╘╙╚"+
            "╛╜╝╞╟╠╡╢╣╤╥╦╧╨╩╪╫╬╭╮╯╰╱╲╳╴╵╶╷╸╹╺╻╼╽╾╿▀▁▂▃▄▅▆▇█▉▊▋▌▍▎▏▐░▒▓▔▕▖▗▘▙▚▛▜▝▞▟■□▢▣▤▥▦▧▨▩▪▫▬▭▮▯▰▱▲△▴▵▶▷▸▹►▻▼▽▾▿◀◁◂"+
            "◃◄◅◆◇◈◉◊○◌◍◎●◐◑◒◓◔◕◖◗◘◙◚◛◜◝◞◟◠◡◢◣◤◥◦◧◨◩◪◫◬◭◮◯◰◱◲◳◴◵◶◷◸◹◺◻◼◽◾◿☀☁☂☃☄★☆☇☈☉☊☋☌☍☎☏☐☑☒☓☔☕☖☗☘☙☚☛☜☝☞☟☠☡☢☣☤☥☦☧☨☩☪"+
            "☫☬☭☮☯☰☱☲☳☴☵☶☷☸☹☺☻☼☽☾☿♀♁♂♃♄♅♆♇♈♉♊♋♌♍♎♏♐♑♒♓♔♕♖♗♘♙♚♛♜♝♞♟♠♡♢♣♤♥♦♧♨♩♪♫♬♭♮♯♲♳♴♵♶♷♸♹♺♻♼♽♾♿⚀⚁⚂⚃⚄⚅⚐⚑⚒⚓⚔⚕⚖⚗⚘⚙⚚"+
            "⚛⚜⚝⚞⚟⚠⚡⚢⚣⚤⚥⚦⚧⚨⚩⚪⚫⚬⚭⚮⚯⚰⚱⚲⚳⚴⚵⚶⚷⚸⚹⚺⚻⚼⛀⛁⛂⛃⛢⛤⛥⛦⛧⛨⛩⛪⛫⛬⛭⛮⛯⛰⛱⛲⛳⛴⛵⛶⛷⛸⛹⛺⛻⛼⛽⛾⛿✁✂✃"+
            "✄✅✆✇✈✉✊✋✌✍✎✏✐✑✒✓✔✕✖✗✘✙✚✛✜✝✞✟✠✡✢✣✤✥✦✧✨✩✪✫✬✭✮✯✰✱✲✳✴✵✶✷✸✹✺✻✼✽✾✿❀❁❂❃❄❅❆❇❈❉❊❋❌❍❎❏❐❑❒❓❔❕❖❗❘❙❚❛❜❝❞❟❠❡❢❣❤❥❦❧➔➘➙➚"+
            "➛➜➝➞➟➠➡➢➣➤➥➦➧➨➩➪➫➬➭➮➯➱➲➳➴➵➶➷➸➹➺➻➼➽➾⟰⟱⟲⟳⟴⟵⟶⟷⟸⟹⟺⟻⟼⟽⟾⟿⤀⤁⤂⤃⤄⤅⤆⤇⤈⤉⤊⤋⤌⤍⤎⤏⤐⤑⤒⤓⤔⤕⤖⤗⤘"+
            "⤙⤚⤛⤜⤝⤞⤟⤠⤡⤢⤣⤤⤥⤦⤧⤨⤩⤪⤫⤬⤭⤮⤯⤰⤱⤲⤳⤴⤵⤶⤷⤸⤹⤺⤻⤼⤽⤾⤿⥀⥁⥂⥃⥄⥅⥆⥇⥈⥉⥊⥋⥌⥍⥎⥏⥐⥑⬀⬁⬂⬃⬄⬅⬆⬇⬈⬉⬊⬋⬌⬍⬎⬏"+
            "⬐⬑⬒⬓⬔⬕⬖⬗⬘⬙⬚ⱠⱡⱣⱥⱦⱭⱯⱰ⸢⸣⸤⸥⸮〃〄ﬀﬁﬂﬃﬄﬅﬆ﴾﴿﷼︐︑︒︓︔︕︖︗︘︙︰︱︲︳︴︵︶︷︸︹︺︻︼︽︾︿﹀﹁﹂﹃﹄﹅﹆﹉﹊﹋﹌﹍﹎﹏﹐﹑﹒﹔﹕﹖﹗﹘﹙﹚﹛﹜"+
            "﹝﹞﹟﹠﹡﹢﹣﹤﹥﹦﹨﹩﹪﹫！＂＃＄％＆＇（）＊＋，－．／：；＜＝＞？＠［＼］＾＿｀｛｜｝～｟｠￠￡￢￣￤￥￦";

    /**
     * Replaces each non-URL-safe  character in the given string with an underscore.
     */
    public static String makeIdSafe(String str) {
        StringBuilder builder = new StringBuilder();
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                Character c = str.charAt(i);
                if (Character.isLetterOrDigit(c) || c.toString().equals("_")) {
                    builder.append(c.toString());
                } else {
                    builder.append("_");
                }
            }
        }
        return builder.toString();
    }
    
    /**
     * Lowercases all the strings in a list.
     */
    public static List<String> lowerCaseAll(List<String> list) {
        List<String> result = new ArrayList<>();
        for (String str : list) {
            result.add(str.toLowerCase());
        }
        return result;
    }
    
    /**
     * Resolves the given Element and XPath to a trimmed String value. Returns empty string if null.
     */
    public static String selectStringTrimmed(Element element, String search) {
        return defaultTrim((String)element.selectObject(search));
    }
    
    /**
     * Trims the incoming String if it is not null.
     */
    public static String trimOrNull(String str) {
        return defaultTrim(str, null);
    }
    
    /**
     * Trims the incoming String, returning empty string if given null.
     */
    public static String defaultTrim(String str) {
        return defaultTrim(str, "");
    }
    
    /**
     * Trims the incoming String, returning the default string if given null.
     */
    public static String defaultTrim(String str, String defaultString) {
        String result = StringUtils.defaultIfBlank(str, defaultString);
        if (result != null) {
            result = result.trim();
        }
        return result;
    }
    
    /**
     * Gets a URI of the given String, or null if not given valid URL input.
     * Yes, this method returns a **URI** only if the given string is a valid **URL**.
     */
    public static URI getValidUrlAsUriOrNull(String str) {
        try {
            return new URL(str).toURI();
        }
        catch (MalformedURLException | URISyntaxException ex) {
            return null;
        }
    }
    
    
    /////////////////////
    // String Analysis //
    /////////////////////
    
    /**
     * Calculates the levenshtein distance, taken from:
     * http://rosettacode.org/wiki/Levenshtein_distance#Java
     */
    public static int levenshteinDistance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
    
    
    ////////////////////
    // String Parsing //
    ////////////////////
    /**
     * Assumes that the stringValue parameter contains a series of pipe-separated values.  This method splits the string
     * on those pipes, and outputs a list of the split values.  For example, input is: 'a|b|c', output is List(a, b, c)
     */
    public static List<String> parsePipeFormat(String rawString) {
        ArrayList<String> result = new ArrayList<>();
        if (rawString != null) {
            String trimmedString = rawString.trim();
            if (trimmedString.length() > 0) {
                String[] rawValues = trimmedString.split("\\|");
                for (String rawValue : rawValues) {
                    result.add(rawValue.trim());
                }
            }
        }
        return result;
    }
    
    /**
     * Assumes that the stringValue parameter contains a series of double-pipe-separated values.  This method splits the string
     * on those pipes, and outputs a list of the split values.  For example, input is: 'a||b||c', output is List(a, b, c)
     */
    public static List<String> parseDoublePipeFormat(String rawString) {
        ArrayList<String> result = new ArrayList<>();
        if (rawString != null) {
            String trimmedString = rawString.trim();
            if (trimmedString.length() > 0) {
                String[] rawValues = trimmedString.split("\\|\\|");
                for (String rawValue : rawValues) {
                    result.add(rawValue.trim());
                }
            }
        }
        return result;
    }
    
    /**
     * Accepts a string of 'name1: desc1|name2: desc2|name3: desc3|...' and outputs a list of pairs, like this:
     * List([name: name1, desc: desc1], [name: name2, desc: desc2], [name: name3, desc: desc3], ...)
     */
    public static List<BulkReadRawData.RawNameDescPair> parseColonPipeFormat(String rawString) {
        List<String> pipeSeparatedCellValue = parsePipeFormat(rawString);
        List<BulkReadRawData.RawNameDescPair> result = new ArrayList<>();
        for (String nameDescPair : pipeSeparatedCellValue) {
            int firstColon = 0;
            boolean foundColon = false;
            while (!foundColon) {
                firstColon = nameDescPair.indexOf(':', firstColon);
                if (firstColon == -1) {
                    throw new UnsupportedOperationException(String.format(
                        "Expecting to find colon in value[%s], but none was found.  Full String: %s",
                        nameDescPair,
                        rawString
                    ));
                }
                if (firstColon > 0 && nameDescPair.charAt(firstColon - 1) != '\\') {
                    foundColon = true;
                }
            }
            String name = defaultTrim(nameDescPair.substring(0, firstColon));
            String desc = defaultTrim(nameDescPair.substring(firstColon + 1));
            result.add(new BulkReadRawData.RawNameDescPair(name, desc));
        }
        return result;
    }
    
    /**
     * Given a list of name:desc pairs as created by the parseColonPipeFormat method, this method will convert that into a
     * Map of [name1: desc1, name2: desc2]. If a name occurs multiple times, its value in the map will be the last desc
     * that it is associated with.
     */
    public static Map<String, String> explodeListNameValuePairs(String rawString) {
        List<BulkReadRawData.RawNameDescPair> colonPipeCellListValue = parseColonPipeFormat(rawString);
        Map<String, String> result = new HashMap<>();
        for (BulkReadRawData.RawNameDescPair nameDescPair : colonPipeCellListValue) {
            result.put(nameDescPair.name, nameDescPair.desc);
        }
        return result;
    }
    
    /**
     * For matching the combined kindInfo (required/kind/values) field in a parameters-pipe formatted string.
     */
    private static final Pattern RAW_KIND_INFO_REGEX = Pattern.compile("^(\\*)?(STRING|NUMBER|BOOLEAN|DATETIME|(ENUM|ENUM_MULTI)\\((.*)\\))$");
    private static final int RAW_KIND_INFO_REQUIRED_GROUP = 1;
    private static final int RAW_KIND_INFO_KIND_GROUP = 2;
    private static final int RAW_KIND_INFO_ENUM_KIND_GROUP = 3;
    private static final int RAW_KIND_INFO_ENUM_VALUES_GROUP = 4;
    
    /**
     * For matching a single enum value in the comma-separated list of a kindInfo field.
     * Matches one or more contiguous characters where each character is:
     * <ul>
     *     <li>not a comma, or</li>
     *     <li>a comma preceded by a backslash</li>
     * </ul>
     */
    private static final Pattern RAW_ENUM_VALUE_REGEX = Pattern.compile("([^,]|(?<=\\\\),)+");
    private static String unescapeRawEnumValue(String rawEnumValue) {
        // Deal with escape sequences: \, \( \) \\
        rawEnumValue = rawEnumValue.replace("\\,", ",");
        rawEnumValue = rawEnumValue.replace("\\(", "(");
        rawEnumValue = rawEnumValue.replace("\\)", ")");
        rawEnumValue = rawEnumValue.replace("\\\\", "\\");
        return rawEnumValue;
    }
    
    /**
     * Accepts a string of 'identifier1|kindInfo1|name1|description1||identifier2|kindInfo2|name2|description2|...' and
     * outputs a list of raw TD parameters.
     * The kindInfo consists of an optional asterisk (denotes a required parameter) followed by a kind name (one of
     * STRING, NUMBER, BOOLEAN, DATETIME, ENUM, or ENUM_MULTI). If the kind name is ENUM or ENUM_MULTI, the name is
     * followed by a parenthesized, comma-separated list of allowed enum values.
     */
    public static List<BulkReadRawData.RawTdParameter> parseParameterPipeFormat(String rawString) {
        List<String> pipeSeparatedCellValue = parseDoublePipeFormat(rawString);
        List<BulkReadRawData.RawTdParameter> result = new ArrayList<>();
        for (String rawParamString : pipeSeparatedCellValue) {
    
            //LogManager.getLogger(BulkImportUtils.class).debug("Parsing parameter from: " + rawParamString);
    
            String[] rawParamStringSplit = rawParamString.split("\\|");
            if (rawParamStringSplit.length != 4) {
                throw new UnsupportedOperationException(String.format(
                    "Expecting to find three pipes in value[%s], but found %d instead.  Full String: %s",
                    rawParamString,
                    rawParamStringSplit.length - 1,
                    rawString
                ));
            }
    
            BulkReadRawData.RawTdParameter rawParam = new BulkReadRawData.RawTdParameter();
            rawParam.id = defaultTrim(rawParamStringSplit[0]);
            String rawKindInfo = defaultTrim(rawParamStringSplit[1]);
            rawParam.name = defaultTrim(rawParamStringSplit[2]);
            rawParam.description = defaultTrim(rawParamStringSplit[3]);
            
            Matcher rawKindInfoMatcher = RAW_KIND_INFO_REGEX.matcher(rawKindInfo);
            if (!rawKindInfoMatcher.matches()) {
                throw new UnsupportedOperationException(String.format(
                    "Requirement/Kind/Values field[%s] does not match required pattern[%s].  Full String: %s",
                    rawKindInfo,
                    RAW_KIND_INFO_REGEX.toString(),
                    rawString
                ));
            }
    
            rawParam.isRequired = rawKindInfoMatcher.group(RAW_KIND_INFO_REQUIRED_GROUP) != null;
    
            //LogManager.getLogger(BulkImportUtils.class).debug("Matched string: " + rawKindInfo);
            //for (int i = 1; i <= rawKindInfoMatcher.groupCount(); ++i) {
            //    LogManager.getLogger(BulkImportUtils.class).debug(String.format("Match #%d: %s", i, rawKindInfoMatcher.group(i)));
            //}
            
            if (rawKindInfoMatcher.group(RAW_KIND_INFO_ENUM_KIND_GROUP) == null) {
                // Non-enum kind
                rawParam.kindName = rawKindInfoMatcher.group(RAW_KIND_INFO_KIND_GROUP);
            }
            else {
                // Enum kind
                rawParam.kindName = rawKindInfoMatcher.group(RAW_KIND_INFO_ENUM_KIND_GROUP);
                String rawEnumValuesList = defaultTrim(rawKindInfoMatcher.group(RAW_KIND_INFO_ENUM_VALUES_GROUP));
                Matcher rawEnumValueMatcher = RAW_ENUM_VALUE_REGEX.matcher(rawEnumValuesList);
                while (rawEnumValueMatcher.find()) {
                    String rawEnumValue = defaultTrim(rawEnumValueMatcher.group(0));
                    String enumValue = unescapeRawEnumValue(rawEnumValue);
                    rawParam.enumValues.add(enumValue);
                }
            }
    
            //LogManager.getLogger(BulkImportUtils.class).debug("Parsed rawParam: " + rawParam.toJson().toString(2));
            
            result.add(rawParam);
        }
        return result;
    }
}
