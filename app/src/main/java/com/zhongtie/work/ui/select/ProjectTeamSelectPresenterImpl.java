package com.zhongtie.work.ui.select;

import android.util.Log;

import com.zhongtie.work.data.ProjectTeamEntity;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.HanziToPinyin;
import com.zhongtie.work.util.JsonUtil;
import com.zhongtie.work.util.TextUtil;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Auth: Chaek
 * Date: 2018/1/11
 */

class ProjectTeamSelectPresenterImpl extends BasePresenterImpl<ProjectTeamSelectContract.View> implements ProjectTeamSelectContract.Presenter {
    private List<ProjectTeamEntity> mProjectTeamEntities;

    @Override
    public void getProjectTeamListData() {
        addDispose(Flowable.just(bank)
                .map(s -> JsonUtil.getPersons(s, String.class))
                .flatMap(Flowable::fromIterable)
                .map(s -> {
                    List<String> list = JsonUtil.getPersons(s, String.class);
                    ProjectTeamEntity item = new ProjectTeamEntity(list.get(0));
                    item.setProjectTeamID(1);
                    return item;
                })
                .toList()
                .toFlowable()
                .flatMap(Flowable::fromIterable)
                .filter(it -> it.getProjectTeamName() != null)
                //转换字符
                .map(projectTeamEntity -> {
                    try {
                        String c = HanziToPinyin.getInstance().get(projectTeamEntity.getProjectTeamName().substring(0, 1)).get(0).target.substring(0, 1);
                        projectTeamEntity.setCharacter(c);
                    } catch (Exception e) {
                        e.printStackTrace();
                        projectTeamEntity.setCharacter("#");
                    }
                    return projectTeamEntity;
                })
                .toList()
                .map(projectTeamEntities -> {
                    //排序
                    Collections.sort(projectTeamEntities, new PinyinComparator());
                    mProjectTeamEntities = projectTeamEntities;
                    return projectTeamEntities;
                })
                .toFlowable()
                .concatMap(this::convertList)
                .toList()
                .toFlowable()
                .compose(Network.netorkIO())
                .subscribe(projectTeamEntities -> mView.setProjectTeamListData(projectTeamEntities), throwable -> {
                }));

    }

    /**
     * 为list按照字幕分组并加上
     */
    private Flowable<ProjectTeamEntity> convertList(List<ProjectTeamEntity> projectTeamEntities) {
        return Flowable.fromIterable(projectTeamEntities)
                .map(ProjectTeamEntity::getCharacter)
                .distinct()
                .toList()
                .toFlowable()
                .flatMap(Flowable::fromIterable)
                .concatMap(s -> Flowable.fromIterable(projectTeamEntities)
                        .filter(projectTeamEntity -> projectTeamEntity.getCharacter().equals(s))
                        .toList()
                        .toFlowable()
                        .concatMap(projectTeamEntities1 -> {
                            Log.d("-----------", "getProjectTeamListData: " + s + "====" + projectTeamEntities1.size());
                            ProjectTeamEntity title = new ProjectTeamEntity(s);
                            title.setProjectTeamID(0);
                            projectTeamEntities1.add(0, title);
                            return Flowable.fromIterable(projectTeamEntities1);
                        }));
    }

    @Override
    public void searchProjectTeamList(String searchContent) {
        addDispose(Observable.fromIterable(this.mProjectTeamEntities)
                .filter(projectTeamEntity -> TextUtil.isEmpty(searchContent) || projectTeamEntity.getProjectTeamName().contains(searchContent))
                .toList()
                .toFlowable()
                .concatMap(this::convertList)
                .toList()
                .toFlowable()
                .compose(Network.netorkIO())
                .subscribe(projectTeamEntities -> mView.setProjectTeamListData(projectTeamEntities), throwable -> {
                }));


    }

    private String bank = "[\n" +
            "    [\n" +
            "        \"中国工商银行\",\n" +
            "        \"102100099996\",\n" +
            "        \"http://admin.goodsogood.com/m/assets/img/banklogo/中国工商银行.png\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"中国农业银行\",\n" +
            "        \"103100000026\",\n" +
            "        \"http://admin.goodsogood.com/m/assets/img/banklogo/中国农业银行.png\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"中国银行\",\n" +
            "        \"104100000004\",\n" +
            "        \"http://admin.goodsogood.com/m/assets/img/banklogo/中国银行.png\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"中国建设银行\",\n" +
            "        \"105100000017\",\n" +
            "        \"http://admin.goodsogood.com/m/assets/img/banklogo/中国建设银行.png\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"交通银行\",\n" +
            "        \"301290000007\",\n" +
            "        \"http://admin.goodsogood.com/m/assets/img/banklogo/交通银行.png\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"中信银行\",\n" +
            "        \"302100011000\",\n" +
            "        \"http://admin.goodsogood.com/m/assets/img/banklogo/中信银行.png\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"光大银行\",\n" +
            "        \"303100000006\",\n" +
            "        \"http://admin.goodsogood.com/m/assets/img/banklogo/中国光大银行.png\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"华夏银行\",\n" +
            "        \"304100040000\",\n" +
            "        \"http://admin.goodsogood.com/m/assets/img/banklogo/华夏银行.png\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"民生银行\",\n" +
            "        \"305100000013\",\n" +
            "        \"http://admin.goodsogood.com/m/assets/img/banklogo/中国民生银行.png\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"广发银行\",\n" +
            "        \"306581000003\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"平安银行\",\n" +
            "        \"307584007998\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"招商银行\",\n" +
            "        \"308584000013\",\n" +
            "        \"http://admin.goodsogood.com/m/assets/img/banklogo/招商银行.png\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"兴业银行\",\n" +
            "        \"309391000011\",\n" +
            "        \"http://admin.goodsogood.com/m/assets/img/banklogo/兴业银行.png\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"上海浦东发展银行\",\n" +
            "        \"310290000013\",\n" +
            "        \"http://admin.goodsogood.com/m/assets/img/banklogo/上海浦东发展银行.png\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"北京银行\",\n" +
            "        \"313100000013\",\n" +
            "        \"http://admin.goodsogood.com/m/assets/img/banklogo/北京银行.png\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"天津银行\",\n" +
            "        \"313110000017\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"河北银行\",\n" +
            "        \"313121006888\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"秦皇岛银行\",\n" +
            "        \"313126001022\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"邯郸市商业银行\",\n" +
            "        \"313127000013\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"邢台银行\",\n" +
            "        \"313131000016\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"张家口市商业银行\",\n" +
            "        \"313138000019\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"承德银行\",\n" +
            "        \"313141052422\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"沧州银行\",\n" +
            "        \"313143005157\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"廊坊银行\",\n" +
            "        \"313146000019\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"衡水银行\",\n" +
            "        \"313148053964\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"晋商银行\",\n" +
            "        \"313161000017\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"晋城银行\",\n" +
            "        \"313168000003\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"晋中银行\",\n" +
            "        \"313175000011\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"内蒙古银行\",\n" +
            "        \"313191000011\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"包商银行\",\n" +
            "        \"313192000013\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"鄂尔多斯银行\",\n" +
            "        \"313205057830\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"大连银行\",\n" +
            "        \"313222080002\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"鞍山市商业银行\",\n" +
            "        \"313223007007\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"锦州银行\",\n" +
            "        \"313227000012\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"葫芦岛银行\",\n" +
            "        \"313227600018\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"吉林银行\",\n" +
            "        \"313241066661\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"龙江银行\",\n" +
            "        \"313261099913\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"上海银行\",\n" +
            "        \"313290000017\",\n" +
            "        \"http://admin.goodsogood.com/m/assets/img/banklogo/上海银行.png\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"南京银行\",\n" +
            "        \"313301008887\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"江苏银行\",\n" +
            "        \"313301099999\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"苏州银行\",\n" +
            "        \"313305066661\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"江苏长江商业银行\",\n" +
            "        \"313312300018\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"杭州银行\",\n" +
            "        \"313331000014\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"宁波银行\",\n" +
            "        \"313332082914\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"宁波通商银行\",\n" +
            "        \"313332090019\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"温州银行\",\n" +
            "        \"313333007331\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"湖州银行\",\n" +
            "        \"313336071575\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"绍兴银行营业部\",\n" +
            "        \"313337009004\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"金华银行\",\n" +
            "        \"313338009688\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"浙江稠州商业银行\",\n" +
            "        \"313338707013\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"台州银行\",\n" +
            "        \"313345001665\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"浙江泰隆商业银行\",\n" +
            "        \"313345010019\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"浙江民泰商业银行\",\n" +
            "        \"313345400010\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"福建海峡银行\",\n" +
            "        \"313391080007\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"厦门银行\",\n" +
            "        \"313393080005\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"泉州银行\",\n" +
            "        \"313397075189\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"南昌银行\",\n" +
            "        \"313421087506\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"九江银行\",\n" +
            "        \"313424076706\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"赣州银行\",\n" +
            "        \"313428076517\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"上饶银行\",\n" +
            "        \"313433076801\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"齐鲁银行\",\n" +
            "        \"313451000019\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"青岛银行\",\n" +
            "        \"313452060150\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"齐商银行\",\n" +
            "        \"313453001017\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"枣庄银行\",\n" +
            "        \"313454000016\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"东营市商业银行\",\n" +
            "        \"313455000018\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"烟台银行\",\n" +
            "        \"313456000108\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"潍坊银行\",\n" +
            "        \"313458000013\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"济宁银行\",\n" +
            "        \"313461000012\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"泰安市商业银行\",\n" +
            "        \"313463000993\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"莱商银行\",\n" +
            "        \"313463400019\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"威海市商业银行\",\n" +
            "        \"313465000010\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"德州银行\",\n" +
            "        \"313468000015\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"临商银行\",\n" +
            "        \"313473070018\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"日照银行\",\n" +
            "        \"313473200011\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"郑州银行\",\n" +
            "        \"313491000232\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"中原银行\",\n" +
            "        \"313491099996\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"开封市商业银行\",\n" +
            "        \"313492070005\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"洛阳银行\",\n" +
            "        \"313493080539\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"平顶山银行\",\n" +
            "        \"313495081900\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"安阳银行\",\n" +
            "        \"313496000014\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"焦作市商业银行\",\n" +
            "        \"313501080608\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"漯河市商业银行\",\n" +
            "        \"313504000010\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"商丘市商业银行\",\n" +
            "        \"313506082510\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"南阳银行\",\n" +
            "        \"313513080408\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"湖北银行\",\n" +
            "        \"313521006000\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"长沙银行\",\n" +
            "        \"313551088886\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"广州银行\",\n" +
            "        \"313581003284\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"广东华兴银行\",\n" +
            "        \"313586000006\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"广东南粤银行\",\n" +
            "        \"313591001001\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"东莞银行\",\n" +
            "        \"313602088017\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"广西北部湾银行\",\n" +
            "        \"313611001018\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"桂林银行\",\n" +
            "        \"313617000018\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"成都银行\",\n" +
            "        \"313651099999\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"重庆银行\",\n" +
            "        \"313653000013\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"攀枝花市商业银行\",\n" +
            "        \"313656000019\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"泸州市商业银行\",\n" +
            "        \"313657092617\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"德阳银行\",\n" +
            "        \"313658000014\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"绵阳市商业银行\",\n" +
            "        \"313659000016\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"乐山市商业银行\",\n" +
            "        \"313665092924\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"南充市商业银行\",\n" +
            "        \"313673093259\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"贵阳市商业银行\",\n" +
            "        \"313701098010\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"富滇银行运营管理部\",\n" +
            "        \"313731010015\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"玉溪市商业银行\",\n" +
            "        \"313741095715\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"西安银行\",\n" +
            "        \"313791000015\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"长安银行\",\n" +
            "        \"313791030003\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"兰州银行\",\n" +
            "        \"313821001016\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"青海银行营业部\",\n" +
            "        \"313851000018\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"昆仑银行\",\n" +
            "        \"313882000012\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"无锡农村商业银行\",\n" +
            "        \"314302066666\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"江苏江阴农村商业银行\",\n" +
            "        \"314302200018\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"江苏江南农村商业银行\",\n" +
            "        \"314304099999\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"太仓农村商业银行\",\n" +
            "        \"314305106644\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"昆山农村商业银行\",\n" +
            "        \"314305206650\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"张家港农村商业银行\",\n" +
            "        \"314305670002\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"广州农村商业银行\",\n" +
            "        \"314581000011\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"佛山顺德农村商业银行\",\n" +
            "        \"314588000016\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"海口联合农村商业银行\",\n" +
            "        \"314641000014\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"成都农村商业银行\",\n" +
            "        \"314651000000\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"重庆农村商业银行\",\n" +
            "        \"314653000011\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"恒丰银行\",\n" +
            "        \"315456000105\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"浙商银行\",\n" +
            "        \"316331000018\",\n" +
            "        \"http://admin.goodsogood.com/m/assets/img/banklogo/浙商银行.png\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"天津农村合作银行\",\n" +
            "        \"317110010019\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"渤海银行\",\n" +
            "        \"318110000014\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"徽商银行\",\n" +
            "        \"319361000013\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"北京顺义银座村镇银行\",\n" +
            "        \"320100010011\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"浙江景宁银座村镇银行\",\n" +
            "        \"320343800019\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"浙江三门银座村镇银行\",\n" +
            "        \"320345790018\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"江西赣州银座村镇银行\",\n" +
            "        \"320428090311\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"东营莱商村镇银行\",\n" +
            "        \"320455000019\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"深圳福田银座村镇银行\",\n" +
            "        \"320584002002\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"重庆渝北银座村镇银行\",\n" +
            "        \"320653000104\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"重庆黔江银座村镇银行\",\n" +
            "        \"320687000016\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"重庆三峡银行\",\n" +
            "        \"321667090019\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"上海农村商业银行\",\n" +
            "        \"322290000011\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"深圳前海微众银行\",\n" +
            "        \"323584000888\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"北京农村商业银行\",\n" +
            "        \"402100000018\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"吉林省农村信用社联合社\",\n" +
            "        \"402241000015\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"江苏省农村信用社联合社\",\n" +
            "        \"402301099998\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"浙江省农村信用社联合社\",\n" +
            "        \"402331000007\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"宁波鄞州农村合作银行\",\n" +
            "        \"402332010004\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"福建省农村信用社联合社\",\n" +
            "        \"402391000068\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"江西省农村信用社联合社\",\n" +
            "        \"402421099990\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"山东省农村信用社联合社\",\n" +
            "        \"402451000010\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"武汉农村商业银行\",\n" +
            "        \"402521090019\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"广东省农村信用社联合社\",\n" +
            "        \"402581090008\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"深圳农村商业银行\",\n" +
            "        \"402584009991\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"东莞农村商业银行\",\n" +
            "        \"402602000018\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"广西壮族自治区农村信用社联合社\",\n" +
            "        \"402611099974\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"四川省农村信用社联合社\",\n" +
            "        \"402651020006\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"云南省农村信用社联合社\",\n" +
            "        \"402731057238\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"宁夏黄河农村商业银行\",\n" +
            "        \"402871099996\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"中国邮政储蓄银行\",\n" +
            "        \"403100000004\",\n" +
            "        \"http://admin.goodsogood.com/m/assets/img/banklogo/中国邮政.png\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"东亚银行\",\n" +
            "        \"502290000006\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"外换银行\",\n" +
            "        \"591110000016\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"友利银行\",\n" +
            "        \"593100000020\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"新韩银行\",\n" +
            "        \"595100000007\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"企业银行\",\n" +
            "        \"596110000013\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"韩亚银行\",\n" +
            "        \"597100000014\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"中德住房储蓄银行\",\n" +
            "        \"717110000010\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"厦门国际银行\",\n" +
            "        \"781393010011\"\n" +
            "    ],\n" +
            "    [\n" +
            "        \"华一银行\",\n" +
            "        \"787290000019\"\n" +
            "    ]\n" +
            "]";

}
