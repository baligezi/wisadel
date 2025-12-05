package wisadelmod.modcore;

import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import wisadelmod.cards.*;
import com.badlogic.gdx.graphics.Color;
import wisadelmod.characters.MyCharacter;
import wisadelmod.relics.MyRelic;

import static wisadelmod.characters.MyCharacter.PlayerColorEnum.Wisadel_COLOR;
import static wisadelmod.characters.MyCharacter.PlayerColorEnum.MY_CHARACTER;

@SpireInitializer
public class WisadelMod implements EditCardsSubscriber , EditStringsSubscriber, EditCharactersSubscriber, EditRelicsSubscriber {
    private static final String MY_CHARACTER_BUTTON = "WisadelModResources/img/charS/Character_Button.png";
    // 人物选择界面的立绘
    private static final String MY_CHARACTER_PORTRAIT = "WisadelModResources/img/charS/Character_Portrait.png";
    // 攻击牌的背景（小尺寸）
    private static final String BG_ATTACK_512 = "WisadelModResources/img/512/bg_attack_512.png";
    // 能力牌的背景（小尺寸）
    private static final String BG_POWER_512 = "WisadelModResources/img/512/bg_power_512.png";
    // 技能牌的背景（小尺寸）
    private static final String BG_SKILL_512 = "WisadelModResources/img/512/bg_skill_512.png";
    // 在卡牌和遗物描述中的能量图标
    private static final String SMALL_ORB = "WisadelModResources/img/char/small_orb.png";
    // 攻击牌的背景（大尺寸）
    private static final String BG_ATTACK_1024 = "WisadelModResources/img/1024/bg_attack.png";
    // 能力牌的背景（大尺寸）
    private static final String BG_POWER_1024 = "WisadelModResources/img/1024/bg_power.png";
    // 技能牌的背景（大尺寸）
    private static final String BG_SKILL_1024 = "WisadelModResources/img/1024/bg_skill.png";
    // 在卡牌预览界面的能量图标
    private static final String BIG_ORB = "WisadelModResources/img/char/card_orb.png";
    // 小尺寸的能量图标（战斗中，牌堆预览）
    private static final String ENEYGY_ORB = "WisadelModResources/img/char/cost_orb.png";
    public static final Color MY_COLOR = new Color(230.0F / 255.0F, 230.0F / 255.0F, 250.0F / 255.0F, 1.0F);
    public WisadelMod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(Wisadel_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR,BG_ATTACK_512,BG_SKILL_512,BG_POWER_512,ENEYGY_ORB,BG_ATTACK_1024,BG_SKILL_1024,BG_POWER_1024,BIG_ORB,SMALL_ORB);
    }

    public static void initialize() {
        new WisadelMod();
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new SheFu());
        BaseMod.addCard(new SiHunLing());
        BaseMod.addCard(new HongtK());
        BaseMod.addCard(new JingXiaHeZi());
        BaseMod.addCard(new BaoLieLiMing());
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new MyCharacter(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT, MY_CHARACTER);
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelic(new MyRelic(), RelicType.SHARED); // RelicType表示是所有角色都能拿到的遗物，还是一个角色的独有遗物
    }

    public void receiveEditStrings() {
        String lang = "ZHS";
        BaseMod.loadCustomStringsFile(CardStrings.class, "WisadelModResources/localization/" + lang + "/cards.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "WisadelModResources/localization/" + lang + "/characters.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "WisadelModResources/localization/" + lang + "/relics.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "WisadelModResources/localization/" + lang + "/powers.json");
    }


}
