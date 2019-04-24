package com.free.app.spp;

import java.util.HashMap;

class Maps {
    static String[] teamEnNameList = {"hawks","celtics","nets","hornets","bulls","cavaliers",
            "mavericks","nuggets","pistons","warriors","rockets","pacers","clippers","lakers",
            "grizzlies","heat","bucks","timberwolves","pelicans","knicks","thunder","magic",
            "76ers","suns","blazers", "kings","spurs","raptors","jazz","wizards"};

    static HashMap<String, String> CntoEng = new HashMap<String, String>() {{
        put("马刺","spurs");
        put("灰熊","grizzlies");
        put("独行侠","mavericks");
        put("火箭","rockets");
        put("鹈鹕","pelicans");
        put("森林狼","timberwolves");
        put("掘金","nuggets");
        put("爵士","jazz");
        put("开拓者","blazers");
        put("雷霆","thunder");
        put("国王","kings");
        put("太阳","suns");
        put("湖人","lakers");
        put("快船","clippers");
        put("勇士","warriors");
        put("热火","heat");
        put("魔术","magic");
        put("老鹰","hawks");
        put("奇才","wizards");
        put("黄蜂","hornets");
        put("活塞","pistons");
        put("步行者","pacers");
        put("凯尔特人","celtics");
        put("76人","76ers");
        put("尼克斯","knicks");
        put("篮网","nets");
        put("猛龙","raptors");
        put("雄鹿","bucks");
        put("公牛","bulls");
        put("骑士","cavaliers");
    }
};

    static HashMap<String, String> EngToCn = new HashMap<String, String>() {
        {
            put("spurs","马刺");
            put("grizzlies","灰熊");
            put("mavericks","独行侠");
            put("rockets","火箭");
            put("pelicans","鹈鹕");
            put("timberwolves","森林狼");
            put("nuggets","掘金");
            put("jazz","爵士");
            put("blazers","开拓者");
            put("thunder","雷霆");
            put("kings","国王");
            put("suns","太阳");
            put("lakers","湖人");
            put("clippers","快船");
            put("warriors","勇士");
            put("heat","热火");
            put("magic","魔术");
            put("hawks","老鹰");
            put("wizards","奇才");
            put("hornets","黄蜂");
            put("pistons","活塞");
            put("pacers","步行者");
            put("celtics","凯尔特人");
            put("76ers","76人");
            put("knicks","尼克斯");
            put("nets","篮网");
            put("raptors","猛龙");
            put("bucks","雄鹿");
            put("bulls","公牛");
            put("cavaliers","骑士");
        }
    };

    static HashMap<String, String> CntoFull = new HashMap<String, String>() {{
        put("马刺","圣安东尼奥马刺");
        put("灰熊","孟菲斯灰熊");
        put("独行侠","达拉斯独行侠");
        put("火箭","休斯顿火箭");
        put("鹈鹕","新奥尔良鹈鹕");
        put("森林狼","明尼苏达森林狼");
        put("掘金","丹佛掘金");
        put("爵士","犹他爵士");
        put("开拓者","波特兰开拓者");
        put("雷霆","俄克拉荷马城雷霆");
        put("国王","萨克拉门托国王");
        put("太阳","菲尼克斯太阳");
        put("湖人","洛杉矶湖人");
        put("快船","洛杉矶快船");
        put("勇士","金州勇士");
        put("热火","迈阿密热火");
        put("魔术","奥兰多魔术");
        put("老鹰","亚特兰大老鹰");
        put("奇才","华盛顿奇才");
        put("黄蜂","夏洛特黄蜂");
        put("活塞","底特律活塞");
        put("步行者","印第安纳步行者");
        put("凯尔特人","波士顿凯尔特人");
        put("76人","费城76人");
        put("尼克斯","纽约尼克斯");
        put("篮网","布鲁克林篮网");
        put("猛龙","多伦多猛龙");
        put("雄鹿","密尔沃基雄鹿");
        put("公牛","芝加哥公牛");
        put("骑士","克利夫兰骑士");
    }
    };
}
