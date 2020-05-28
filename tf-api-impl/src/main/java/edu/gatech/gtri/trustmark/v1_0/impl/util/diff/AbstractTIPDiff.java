package edu.gatech.gtri.trustmark.v1_0.impl.util.diff;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.model.Contact;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import edu.gatech.gtri.trustmark.v1_0.util.diff.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brad on 4/15/16.
 */
public abstract class AbstractTIPDiff extends AbstractDiff implements TrustInteroperabilityProfileDiff {

    private static Boolean COMPARATOR_LOCK = Boolean.FALSE;
    protected static DiffComparator CACHED_COMPARATOR = null;
    protected static DiffComparator loadComparator() {
        synchronized (COMPARATOR_LOCK){
            if( CACHED_COMPARATOR == null )
                CACHED_COMPARATOR = FactoryLoader.getInstance(DiffComparator.class);
        }
        return CACHED_COMPARATOR;
    }


    protected String stringifyEntities(List<Entity> entities){
        StringBuilder b = new StringBuilder();
        stringifyEntities(b, entities);
        return b.toString();
    }

    protected void stringifyEntities(StringBuilder builder, List<Entity> entities){
        if( entities != null && entities.size() > 0 ){
            for( Entity e : entities ){
                stringify(builder, e);
            }
        }
    }

    protected String stringify(Entity entity){
        StringBuilder b = new StringBuilder();
        stringify(b, entity);
        return b.toString();
    }

    protected void stringify(StringBuilder builder, Entity entity){
        builder.append(entity.getIdentifier().toString().toLowerCase()).append("|");
        if( isNotEmpty(entity.getName()) )
            builder.append(entity.getName()).append("|");
        stringifyContacts(builder, entity.getContacts());
    }


    protected String stringify(List<Contact> contacts){
        StringBuilder contactsBuilder1 = new StringBuilder();
        stringifyContacts(contactsBuilder1, contacts);
        return contactsBuilder1.toString();
    }

    protected void stringifyContacts(StringBuilder builder, List<Contact> contacts){
        if( contacts != null && contacts.size() > 0 ){
            for( Contact c : contacts ){
                stringify(builder, c);
            }
        }
    }

    protected String stringify(Contact c){
        StringBuilder b = new StringBuilder();
        stringify(b, c);
        return b.toString();
    }
    protected void stringify(StringBuilder builder, Contact c){
        if( c.getKind() != null )
            builder.append(c.getKind().toString()).append("|");

        if( isNotEmpty(c.getResponder()) )
            builder.append(c.getResponder()).append("|");

        if( c.getEmails() != null && c.getEmails().size() > 0 ){
            for( String email : c.getEmails() ){
                builder.append(email).append("|");
            }
        }

        if( c.getTelephones() != null && c.getTelephones().size() > 0 ){
            for( String telephone : c.getTelephones() ){
                builder.append(telephone).append("|");
            }
        }

        if( c.getPhysicalAddresses() != null && c.getPhysicalAddresses().size() > 0 ){
            for( String addr : c.getPhysicalAddresses() ){
                builder.append(addr).append("|");
            }
        }

        if( c.getMailingAddresses() != null && c.getMailingAddresses().size() > 0 ){
            for( String addr : c.getMailingAddresses() ){
                builder.append(addr).append("|");
            }
        }

        if( c.getWebsiteURLs() != null && c.getWebsiteURLs().size() > 0 ){
            for( URL url : c.getWebsiteURLs() ){
                builder.append(url.toString()).append("|");
            }
        }

        if( isNotEmpty(c.getNotes()) ){
            builder.append(c.getNotes()).append("|");
        }

    }

    protected String stringify(TrustmarkFrameworkIdentifiedObject tfio){
        StringBuilder builder = new StringBuilder();
        stringify(builder, tfio);
        return builder.toString();
    }

    protected void stringify(StringBuilder builder, TrustmarkFrameworkIdentifiedObject tfio){
        builder.append(tfio.getIdentifier().toString().toLowerCase()).append("|");
        if( isNotEmpty(tfio.getName()) )
            builder.append(tfio.getName()).append("|");
        if( isNotEmpty(tfio.getVersion()) )
            builder.append(tfio.getVersion()).append("|");
        if( isNotEmpty(tfio.getDescription()) )
            builder.append(tfio.getDescription()).append("|");
    }



    protected void checkField(ArrayList<TrustInteroperabilityProfileDiffResult> results, String f1, String f2, String idFieldName, Boolean caseSensitive, TrustInteroperabilityProfileDiffType diffTypeOnDifferent, DiffSeverity severityOnDifferent){
        if( isEmpty(f1) && !isEmpty(f2) ){
            append(results, f1, f2, idFieldName, diffTypeOnDifferent, severityOnDifferent);
        }else if( !isEmpty(f1) && isEmpty(f2) ){
            append(results, f1, f2, idFieldName, diffTypeOnDifferent, severityOnDifferent);
        }else if( isNotEmpty(f1) && isNotEmpty(f2) ){
            if( caseSensitive && !f1.equals(f2) ){
                append(results, f1, f2, idFieldName, diffTypeOnDifferent, severityOnDifferent);
            }else if( !caseSensitive && !f1.equalsIgnoreCase(f2) ){
                append(results, f1, f2, idFieldName, diffTypeOnDifferent, severityOnDifferent);
            }
        }
    }

    protected void append( ArrayList<TrustInteroperabilityProfileDiffResult> results, String f1, String f2, String idFieldName, TrustInteroperabilityProfileDiffType diffTypeOnDifferent, DiffSeverity severityOnDifferent) {
        TrustInteroperabilityProfileDiffResultImpl result = null;
        if( diffTypeOnDifferent == TrustInteroperabilityProfileDiffType.REFERENCE_MISMATCH ){
            if( isEmpty(f1) ){
                result = new TrustInteroperabilityProfileDiffResultImpl(
                        diffTypeOnDifferent, severityOnDifferent,
                        idFieldName, "TIP 2 contained reference["+f2+"] which was unmatched in TIP 1.");
            }else{
                result = new TrustInteroperabilityProfileDiffResultImpl(
                        diffTypeOnDifferent, severityOnDifferent,
                        idFieldName, "TIP 1 contained reference["+f1+"] which was unmatched in TIP 2.");
            }
        }else{
            result = new TrustInteroperabilityProfileDiffResultImpl(
                    diffTypeOnDifferent, severityOnDifferent,
                    idFieldName, "The "+idFieldName+"["+f1+"] does not match the corresponding one["+f2+"]");
        }
        result.setData("tip1."+idFieldName, f1);
        result.setData("tip2."+idFieldName, f2);
        results.add(result);
    }


}
