package com.example.a1111.sprots;

import java.util.HashMap;
import java.util.Map;

class AllMap {
    private String[] teamEnNameList = new String[]{"atlanta-hawks", "boston-celtics", "brooklyn-nets",
            "charlotte-hornets", "chicago-bulls", "cleveland-cavaliers", "dallas-mavericks",
            "denver-nuggets", "detroit-pistons", "golden-state-warriors", "houston-rockets",
            "indiana-pacers", "los-angeles-clippers", "los-angeles-lakers",  "memphis-grizzlies",
            "miami-heat", "milwaukee-bucks", "minnesota-timberwolves", "new-orleans-pelicans",
            "new-york-knicks", "oklahoma-city-thunder", "orlando-magic", "philadelphia-sixers",
            "phoenix-suns", "portland-trail-blazers", "sacramento-kings", "san-antonio-spurs",
            "toronto-raptors", "utah-jazz", "washington-wizards"};

    private String[] teamEnLocNameList = {"hawks","celtics","nets","hornets","bulls","cavaliers",
            "mavericks","nuggets","pistons","warriors","rockets","pacers","clippers","lakers",
            "grizzlies","heat","bucks","timberwolves","pelicans","knicks","thunder","magic",
            "sixers","suns","blazers", "kings","spurs","raptors","jazz","wizards"};

    private String[] teamCnNameList = {"老鹰","凯尔特人","篮网","黄蜂",
            "公牛","骑士","独行侠","掘金","活塞","勇士","火箭",
            "步行者","快船","湖人","灰熊","热火","雄鹿","森林狼",
            "鹈鹕","尼克斯","雷霆","魔术","76人","太阳","开拓者",
            "国王","马刺","猛龙","爵士","奇才"};
    private String[] teamCnLocNameList = {"亚特兰大老鹰","波士顿凯尔特人","布鲁克林篮网","夏洛特黄蜂",
            "芝加哥公牛","克利夫兰骑士","达拉斯独行侠","丹佛掘金","底特律活塞","金州勇士","休斯顿火箭",
            "印第安纳步行者","洛杉矶快船","洛杉矶湖人","孟菲斯灰熊","迈阿密热火","密尔沃基雄鹿","明尼苏达森林狼",
            "新奥尔良鹈鹕","纽约尼克斯","俄克拉荷马雷霆","奥兰多魔术","费城76人","菲尼克斯太阳","波特兰开拓者",
            "萨克拉门托国王","圣安东尼奥马刺","多伦多猛龙","犹他爵士","华盛顿奇才"};
    Map<String, String> enName2cnName = new HashMap<>();
    Map<String, String> enLocName2cnName = new HashMap<>();
    Map<String, String> enName2cnLocName = new HashMap<>();
    Map<String, String> enLocName2cnLocName = new HashMap<>();
    Map<String, String> cnName2enName = new HashMap<>();
    Map<String, Integer> cnName2id = new HashMap<>();

    AllMap(){buildMap();}

    private void buildMap() {
        for (int i = 0; i < 30; i ++) {
            cnName2enName.put(teamCnNameList[i], teamEnNameList[i]);
        }
        cnName2id.put("老鹰", R.drawable.hawks);
        cnName2id.put("凯尔特人", R.drawable.celtics);
        cnName2id.put("篮网", R.drawable.nets);
        cnName2id.put("黄蜂", R.drawable.hornets);
        cnName2id.put("公牛", R.drawable.bulls);
        cnName2id.put("骑士", R.drawable.cavaliers);
        cnName2id.put("独行侠", R.drawable.mavericks);
        cnName2id.put("掘金", R.drawable.nuggets);
        cnName2id.put("活塞", R.drawable.pistons);
        cnName2id.put("勇士", R.drawable.warriors);
        cnName2id.put("火箭", R.drawable.rockets);
        cnName2id.put("步行者", R.drawable.pacers);
        cnName2id.put("快船", R.drawable.clippers);
        cnName2id.put("湖人", R.drawable.lakers);
        cnName2id.put("灰熊", R.drawable.grizzlies);
        cnName2id.put("热火", R.drawable.heat);
        cnName2id.put("雄鹿", R.drawable.bucks);
        cnName2id.put("森林狼", R.drawable.timberwolves);
        cnName2id.put("鹈鹕", R.drawable.pelicans);
        cnName2id.put("尼克斯", R.drawable.knicks);
        cnName2id.put("雷霆", R.drawable.thunder);
        cnName2id.put("魔术", R.drawable.magic);
        cnName2id.put("76人", R.drawable.sixers);
        cnName2id.put("太阳", R.drawable.suns);
        cnName2id.put("开拓者", R.drawable.blazers);
        cnName2id.put("国王", R.drawable.kings);
        cnName2id.put("马刺", R.drawable.spurs);
        cnName2id.put("猛龙", R.drawable.raptors);
        cnName2id.put("爵士", R.drawable.jazz);
        cnName2id.put("奇才", R.drawable.wizards);
    }

    String getEnNameFromCnName(String cnName) {
        return cnName2enName.get(cnName);
    }

    int getDrawableId(String name) {
        return cnName2id.get(name);
    }
}
