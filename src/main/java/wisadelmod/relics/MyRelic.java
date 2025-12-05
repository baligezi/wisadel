package wisadelmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import wisadelmod.helpers.ModHelper;
import wisadelmod.power.AncestorPower;


public class MyRelic extends CustomRelic {
    public static final String ID = ModHelper.makePath("MyRelic");
    // 图片路径（大小128x128，可参考同目录的图片）
    private static final String IMG_PATH = "WisadelModResources/img/relics/CuteW.png";
    // 遗物未解锁时的轮廓。可以不使用。如果要使用，取消注释
    private static final String OUTLINE_PATH = "WisadelModResources/img/relics/CuteW_o.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public MyRelic() {
        //super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new MyRelic();
    }
    @Override
    public void atBattleStart() {
        super.atBattleStart();
        AbstractPlayer p = AbstractDungeon.player;
        //this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
        this.addToBot(new ApplyPowerAction(p, p, new AncestorPower(p, 1), 1));
    }
}
