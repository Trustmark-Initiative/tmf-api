package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Contact;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brad on 1/7/16.
 */
public final class ContactJsonProducer implements JsonProducer<Contact, JSONObject> {


    @Override
    public Class<Contact> getSupportedType() {
        return Contact.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(Contact contact) {
        JSONObject jsonObject = new JSONObject();

        if (contact.getKind() != null)
            jsonObject.put("Kind", contact.getKind().toString());
        if (contact.getResponder() != null) {
            jsonObject.put("Responder", contact.getResponder());
        }

        if (contact.getEmails().size() > 1) {
            setField(jsonObject, "Emails", contact.getEmails());
        } else if (contact.getEmails().size() == 1) {
            jsonObject.put("Email", contact.getEmails().get(0));
        }

        if (contact.getTelephones().size() > 1) {
            setField(jsonObject, "Telephones", contact.getTelephones());
        } else if (contact.getTelephones().size() == 1) {
            jsonObject.put("Telephone", contact.getTelephones().get(0));
        }

        if (contact.getPhysicalAddresses().size() > 1) {
            setField(jsonObject, "PhysicalAddresses", contact.getPhysicalAddresses());
        } else if (contact.getPhysicalAddresses().size() == 1) {
            jsonObject.put("PhysicalAddress", contact.getPhysicalAddresses().get(0));
        }

        if (contact.getMailingAddresses().size() > 1) {
            setField(jsonObject, "MailingAddresses", contact.getMailingAddresses());
        } else if (contact.getMailingAddresses().size() == 1) {
            jsonObject.put("MailingAddress", contact.getMailingAddresses().get(0));
        }

        if (contact.getWebsiteURLs().size() > 1) {
            setFieldUrl(jsonObject, "WebsiteURLs", contact.getWebsiteURLs());
        } else if (contact.getWebsiteURLs().size() == 1) {
            jsonObject.put("WebsiteURL", contact.getWebsiteURLs().get(0).toString());
        }

        if (contact.getNotes() != null)
            jsonObject.put("Notes", contact.getNotes());

        return jsonObject;
    }

    private void setFieldUrl(JSONObject json, String field, List<URL> things) {
        List<String> urlsAsStrings = new ArrayList<>();
        if (things != null && !things.isEmpty()) {
            for (URL url : things) {
                urlsAsStrings.add(url.toString());
            }
            setField(json, field, urlsAsStrings);
        }
    }

    private void setField(JSONObject json, String field, List<String> things) {
        Object value = buildObjectOrList(things);
        if (value != null) {
            json.put(field, value);
        }
    }

    private Object buildObjectOrList(List<String> things) {
        if (things.isEmpty())
            return null;
        if (things.size() == 1) {
            return things.get(0);
        } else {
            JSONArray jsonArray = new JSONArray();
            for (Object obj : things) {
                jsonArray.put(obj);
            }
            return jsonArray;
        }
    }


}
