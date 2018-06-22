package com.specifix.pureleagues.model;

public class FootballAssociation {
    private long mAssociationId;
    private String mLogoUrl;
    private String mName;

    public FootballAssociation(long id, String url, String name) {
        mAssociationId = id;
        mLogoUrl = url;
        mName = name;
    }

    public long getAssociationId() {
        return mAssociationId;
    }

    public void setAssociationId(long mAssociationId) {
        this.mAssociationId = mAssociationId;
    }

    public String getLogoUrl() {
        return mLogoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.mLogoUrl = logoUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FootballAssociation that = (FootballAssociation) o;

        if (!mLogoUrl.equals(that.mLogoUrl)) return false;
        return mName.equals(that.mName);

    }

    @Override
    public int hashCode() {
        int result = mLogoUrl.hashCode();
        result = 31 * result + mName.hashCode();
        return result;
    }
}
