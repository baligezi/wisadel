package wisadelmod.power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import wisadelmod.helpers.ModHelper;

public class AncestorPower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = ModHelper.makePath("AncestorPower");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AncestorPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.amount = Amount;

        // 添加一大一小两张能力图
        String path128 = "WisadelModResources/img/powers/ZuZong 84.png";
        String path48 = "WisadelModResources/img/powers/ZuZong 32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 重写叠加能力的方法，确保层数不超过3
    @Override
    public void stackPower(int stackAmount) {
        // 计算叠加后的总层数
        int newAmount = this.amount + stackAmount;
        // 确保总层数不超过3
        this.amount = Math.min(newAmount, 3);
        // 更新描述
        this.updateDescription();
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        // 非荆棘伤害，非生命流失伤害，伤害来源不为空，伤害来源不是能力持有者本身，伤害大于0
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner && damageAmount > 0 && this.amount > 0) {
            // 能力闪烁一下
            this.flash();
            AbstractPlayer p = AbstractDungeon.player;
            // 减少1层能力
            this.addToBot(new ReducePowerAction(p, p, AncestorPower.POWER_ID, 1));
        }
        return damageAmount;
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        // 对随机怪物造成2点伤害
        for (int i = 0; i < this.amount; i++) {
            AbstractMonster m = AbstractDungeon.getRandomMonster();
            if (m != null && !m.isDead && !m.isDying) {
                this.addToBot(new DamageAction(m, new DamageInfo(null, 2, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
        }
    }


    // 回合结束时给予3层格挡
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        AbstractPlayer p = AbstractDungeon.player;
        for (int i = 0; i < this.amount; i++) {
            // 给予3层格挡
            this.addToBot(new GainBlockAction(p, p, 3));
            this.flash();
        }
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        if (DESCRIPTIONS.length > 1) {
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0];
            this.flash();
        }
    }
}
