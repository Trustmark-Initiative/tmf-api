package edu.gatech.gtri.trustmark.v1_0.impl;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.TrustmarkFramework;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class TrustmarkFrameworkImpl implements TrustmarkFramework {

    private final ResourceBundle resourceBundleForTfApi = ResourceBundle.getBundle("tf-api");
    private final ResourceBundle resourceBundleForTfApiImpl = ResourceBundle.getBundle("tf-api-impl");

    public TrustmarkFrameworkImpl() {
        validateSystem();
    }

    @Override
    public String getTrustmarkFrameworkVersion() {
        return resourceBundleForTfApi.getString("trustmark.framework.version");
    }

    @Override
    public String getTrustmarkFrameworkVersionImpl() {
        return resourceBundleForTfApi.getString("trustmark.framework.version");
    }

    @Override
    public String getApiVersion() {
        return resourceBundleForTfApi.getString("project.version");
    }

    @Override
    public String getApiParentVersion() {
        return resourceBundleForTfApi.getString("project.parent.version");
    }

    @Override
    public String getApiBuildDate() {
        return resourceBundleForTfApi.getString("timestamp");
    }

    @Override
    public String getApiImplVersion() {
        return resourceBundleForTfApiImpl.getString("project.version");
    }

    @Override
    public String getApiImplParentVersion() {
        return resourceBundleForTfApiImpl.getString("project.parent.version");
    }

    @Override
    public String getApiImplBuildDate() {
        return resourceBundleForTfApiImpl.getString("timestamp");
    }

    @Override
    public void validateSystem() {

        if (!getTrustmarkFrameworkVersion().equals(getTrustmarkFrameworkVersionImpl())) {
            throw new RuntimeException(format("The Trustmark Framework version for the API ('%s') does not match the Trustmark Framework version for the implementation ('%s').", getTrustmarkFrameworkVersion(), getTrustmarkFrameworkVersionImpl()));
        }

        if (!getApiParentVersion().equals(getApiImplParentVersion())) {
            throw new RuntimeException(format("The parent version for the API ('%s') does not match the parent version for the implementation ('%s').", getApiParentVersion(), getApiImplParentVersion()));
        }

        final List<String> list = Arrays.stream(new Class[]{
                        URIResolver.class
                })
                .map(clazz -> FactoryLoader.getInstance(clazz) == null ? Optional.ofNullable(format("The system requires an implementation of '%s'.", clazz.getCanonicalName())) : Optional.<String>empty())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        if (!list.isEmpty()) {
            throw new RuntimeException(list.stream().collect(Collectors.joining("\n")));
        }
    }
}
