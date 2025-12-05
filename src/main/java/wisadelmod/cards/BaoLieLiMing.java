package wisadelmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import wisadelmod.helpers.ModHelper;

import static wisadelmod.characters.MyCharacter.PlayerColorEnum.Wisadel_COLOR;

public class BaoLieLiMing extends CustomCard {
    //public static final String ID = "WisadelMod:BaoLieLiMing";==↓
    public static final String ID = ModHelper.makePath("BaoLieLiMing");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "WisadelModResources/img/cards/BaoLieLiMing.png";
    private static final int COST = 1;
    //private static final String DESCRIPTION = "造成 !D! 点伤害。";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = Wisadel_COLOR;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public BaoLieLiMing() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 7;
    }

    @Override
    public void upgrade() {
        if (canUpgrade()) {
            this.upgradeName();
            this.upgradeDamage(4);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = CARD_STRINGS.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    @Override
    public boolean canUpgrade() {
        if (this.type == AbstractCard.CardType.CURSE) {
            return false;
        } else if (this.type == AbstractCard.CardType.STATUS) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster selectedMonster) {
        // 获取场上所有「可攻击」的怪物（排除已死亡、无敌的怪物）
        MonsterGroup allMonsters = AbstractDungeon.getMonsters();

        // 遍历所有可攻击的怪物，逐个施加伤害
        for (AbstractMonster m : allMonsters.monsters) {
            if (!m.isDead && !m.isDying && !m.escaped) {
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAction(
                                m, // 目标：当前遍历到的怪物
                                new DamageInfo(
                                        p, // 伤害来源：玩家
                                        m == selectedMonster ? damage : damage / 2, // 伤害值（升级后会自动更新）
                                        DamageInfo.DamageType.NORMAL // 伤害类型：无法格挡（保持原逻辑）
                                ),
                                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL // 攻击特效（可选，增强视觉）
                        )
                );
            }
        }
    }
}
