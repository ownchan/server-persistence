package org.ownchan.server.persistence.model;

import java.util.Date;

import org.ownchan.server.joint.persistence.template.RtcConversationTemplate;
import org.ownchan.server.joint.persistence.template.link.RtcConversationLinkTemplate;
import org.ownchan.server.persistence.dao.RtcConversationDao;
import org.ownchan.server.persistence.util.StaticContextAccessor;

public class DbRtcConversation
    extends
      PersistableObject<DbRtcConversation, RtcConversationTemplate, RtcConversationLinkTemplate, RtcConversationDao>
    implements
      RtcConversationTemplate,
      RtcConversationLinkTemplate {

  private static RtcConversationDao dao;

  private long id;

  private Long requesterUserId;

  private Long responderUserId;

  private Date requesterBeaconTime;

  private Date responderBeaconTime;

  private String requesterOffer;

  private String responderAnswer;

  private Date createTime;

  private Date updateTime;

  private volatile DbUser linkedRequesterUser;

  private volatile DbUser linkedResponderUser;

  @Override
  public long getId() {
    return id;
  }

  @Override
  public void setId(long id) {
    this.id = id;
  }

  @Override
  public Long getRequesterUserId() {
    return requesterUserId;
  }

  public void setRequesterUserId(Long requesterUserId) {
    this.requesterUserId = requesterUserId;
  }

  @Override
  public Long getResponderUserId() {
    return responderUserId;
  }

  public void setResponderUserId(Long responderUserId) {
    this.responderUserId = responderUserId;
  }

  @Override
  public Date getRequesterBeaconTime() {
    return requesterBeaconTime;
  }

  public void setRequesterBeaconTime(Date requesterBeaconTime) {
    this.requesterBeaconTime = requesterBeaconTime;
  }

  @Override
  public Date getResponderBeaconTime() {
    return responderBeaconTime;
  }

  public void setResponderBeaconTime(Date responderBeaconTime) {
    this.responderBeaconTime = responderBeaconTime;
  }

  @Override
  public String getRequesterOffer() {
    return requesterOffer;
  }

  public void setRequesterOffer(String requesterOffer) {
    this.requesterOffer = requesterOffer;
  }

  @Override
  public String getResponderAnswer() {
    return responderAnswer;
  }

  public void setResponderAnswer(String responderAnswer) {
    this.responderAnswer = responderAnswer;
  }

  @Override
  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  @Override
  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  @Override
  public DbUser getLinkedRequesterUser() {
    return linkedRequesterUser;
  }

  @Override
  public DbUser getLinkedResponderUser() {
    return linkedResponderUser;
  }

  @Override
  protected RtcConversationDao getDao() {
    if (dao == null) {
      dao = StaticContextAccessor.getBean(RtcConversationDao.class);
    }
    return dao;
  }

}
