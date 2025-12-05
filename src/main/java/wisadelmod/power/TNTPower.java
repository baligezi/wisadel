package wisadelmod.power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import wisadelmod.helpers.ModHelper;

public class TNTPower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = ModHelper.makePath("TNTPower");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TNTPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.DEBUFF;

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        /*  if (Amount > 3) {
            this.amount = 3;
        }else {
            this.amount = Amount;
        }   */
        this.amount = Math.min(Amount, 3);


        // 添加一大一小两张能力图
        String path128 = "WisadelModResources/img/powers/Potato 84.png";
        String path48 = "WisadelModResources/img/powers/Potato 32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        this.addToBot(new ApplyPowerAction(m, m, new TNTPower(m, -1), -1));
        if (this.amount == 0) {
            this.flash();
            if (m != null && !m.isDead && !m.isDying) {
                this.addToBot(new DamageAction(m, new DamageInfo(null, 30, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
            this.addToBot(new RemoveSpecificPowerAction(m, m, TNTPower.POWER_ID));
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
