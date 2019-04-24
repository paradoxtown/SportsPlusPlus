package com.free.app.spp;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

class AllMap {
    private String[] teamEnLocNameList = new String[]{"atlanta-hawks", "boston-celtics", "brooklyn-nets",
            "charlotte-hornets", "chicago-bulls", "cleveland-cavaliers", "dallas-mavericks",
            "denver-nuggets", "detroit-pistons", "golden-state-warriors", "houston-rockets",
            "indiana-pacers", "los-angeles-clippers", "los-angeles-lakers",  "memphis-grizzlies",
            "miami-heat", "milwaukee-bucks", "minnesota-timberwolves", "new-orleans-pelicans",
            "new-york-knicks", "oklahoma-city-thunder", "orlando-magic", "philadelphia-sixers",
            "phoenix-suns", "portland-trail-blazers", "sacramento-kings", "san-antonio-spurs",
            "toronto-raptors", "utah-jazz", "washington-wizards"};

    private String[] teamEnNameList = {"hawks","celtics","nets","hornets","bulls","cavaliers",
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
            "新奥尔良鹈鹕","纽约尼克斯","俄克拉荷马城雷霆","奥兰多魔术","费城76人","菲尼克斯太阳","波特兰开拓者",
            "萨克拉门托国王","圣安东尼奥马刺","多伦多猛龙","犹他爵士","华盛顿奇才"};
    Map<String, String> enName2cnName = new HashMap<>();
    Map<String, String> enLocName2cnName = new HashMap<>();
    Map<String, String> enName2cnLocName = new HashMap<>();
    Map<String, String> enLocName2cnLocName = new HashMap<>();
    Map<String, String> cnName2enName = new HashMap<>();
    Map<String, Integer> cnName2id = new HashMap<>();
    Map<String, Bitmap> cnName2bitmap = new HashMap<>();
    Map<String, String> cnLocName2cnName = new HashMap<>();

    AllMap(){buildMap();}

    private void buildMap() {
        for (int i = 0; i < 30; i ++) {
            cnName2enName.put(teamCnNameList[i], teamEnNameList[i]);
        }
        for (int i = 0; i < 30; i ++) {
            cnLocName2cnName.put(teamCnLocNameList[i], teamCnNameList[i]);
        }
        cnName2id.put("老鹰", R.mipmap.hawks);
        cnName2id.put("凯尔特人", R.mipmap.celtics);
        cnName2id.put("篮网", R.mipmap.nets);
        cnName2id.put("黄蜂", R.mipmap.hornets);
        cnName2id.put("公牛", R.mipmap.bulls);
        cnName2id.put("骑士", R.mipmap.cavaliers);
        cnName2id.put("独行侠", R.mipmap.mavericks);
        cnName2id.put("掘金", R.mipmap.nuggets);
        cnName2id.put("活塞", R.mipmap.pistons);
        cnName2id.put("勇士", R.mipmap.warriors);
        cnName2id.put("火箭", R.mipmap.rockets);
        cnName2id.put("步行者", R.mipmap.pacers);
        cnName2id.put("快船", R.mipmap.clippers);
        cnName2id.put("湖人", R.mipmap.lakers);
        cnName2id.put("灰熊", R.mipmap.grizzlies);
        cnName2id.put("热火", R.mipmap.heat);
        cnName2id.put("雄鹿", R.mipmap.bucks);
        cnName2id.put("森林狼", R.mipmap.timberwolves);
        cnName2id.put("鹈鹕", R.mipmap.pelicans);
        cnName2id.put("尼克斯", R.mipmap.knicks);
        cnName2id.put("雷霆", R.mipmap.thunder);
        cnName2id.put("魔术", R.mipmap.magic);
        cnName2id.put("76人", R.mipmap.sixers);
        cnName2id.put("太阳", R.mipmap.suns);
        cnName2id.put("开拓者", R.mipmap.blazers);
        cnName2id.put("国王", R.mipmap.kings);
        cnName2id.put("马刺", R.mipmap.spurs);
        cnName2id.put("猛龙", R.mipmap.raptors);
        cnName2id.put("爵士", R.mipmap.jazz);
        cnName2id.put("奇才", R.mipmap.wizards);
    }

    String getEnNameFromCnName(String cnName) {
        return cnName2enName.get(cnName);
    }

    int getDrawableId(String name) {
        return cnName2id.get(name);
    }

    String getCnNameFromCnLocName(String cnLocName){
        return cnLocName2cnName.get(cnLocName);
    }
}
