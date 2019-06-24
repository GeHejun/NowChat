package com.ghj.chat;

import io.netty.channel.Channel;
import lombok.Builder;

import java.util.Date;
/**
 * @author GeHejun
 * @date 2019-06-24
 */
@Builder
public class Session {

    Channel channel;

    /**
     * 主键、自增
     */
    private Integer id;

    /**
     * 登陆账号
     */
    private String loginName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 性别
     */
    private Boolean sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 简介
     */
    private String intro;

    /**
     * 头像
     */
    private String headPortrait;

    /**
     * 生肖
     */
    private String zodiac;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 星座
     */
    private String constellation;

    /**
     * 血型
     */
    private String bloodType;

    /**
     * 毕业学校
     */
    private String schoolTag;

    /**
     * 职业
     */
    private String vocation;

    /**
     * 国家ID
     */
    private Integer nationId;

    /**
     * 省份ID
     */
    private Integer proviceId;

    /**
     * 城市ID
     */
    private Integer cityId;

    /**
     * 用户状态ID
     */
    private Integer userStateId;

    /**
     * 好友策略ID
     */
    private Integer friendshipPolicyId;

    private String friendPolicyQuestion;

    /**
     *  好友策略答案
     */
    private String friendPolicyAnswer;

    /**
     *  好友策略密码
     */
    private String friendPolicyPassword;


    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getSchoolTag() {
        return schoolTag;
    }

    public void setSchoolTag(String schoolTag) {
        this.schoolTag = schoolTag;
    }

    public String getVocation() {
        return vocation;
    }

    public void setVocation(String vocation) {
        this.vocation = vocation;
    }

    public Integer getNationId() {
        return nationId;
    }

    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }

    public Integer getProviceId() {
        return proviceId;
    }

    public void setProviceId(Integer proviceId) {
        this.proviceId = proviceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getUserStateId() {
        return userStateId;
    }

    public void setUserStateId(Integer userStateId) {
        this.userStateId = userStateId;
    }

    public Integer getFriendshipPolicyId() {
        return friendshipPolicyId;
    }

    public void setFriendshipPolicyId(Integer friendshipPolicyId) {
        this.friendshipPolicyId = friendshipPolicyId;
    }

    public String getFriendPolicyQuestion() {
        return friendPolicyQuestion;
    }

    public void setFriendPolicyQuestion(String friendPolicyQuestion) {
        this.friendPolicyQuestion = friendPolicyQuestion;
    }

    public String getFriendPolicyAnswer() {
        return friendPolicyAnswer;
    }

    public void setFriendPolicyAnswer(String friendPolicyAnswer) {
        this.friendPolicyAnswer = friendPolicyAnswer;
    }

    public String getFriendPolicyPassword() {
        return friendPolicyPassword;
    }

    public void setFriendPolicyPassword(String friendPolicyPassword) {
        this.friendPolicyPassword = friendPolicyPassword;
    }
}
