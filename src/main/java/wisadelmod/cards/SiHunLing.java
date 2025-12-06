package wisadelmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wisadelmod.helpers.ModHelper;
import wisadelmod.power.AncestorPower;
import static wisadelmod.characters.MyCharacter.PlayerColorEnum.Wisadel_COLOR;


public class SiHunLing extends CustomCard {
    public static final String ID = ModHelper.makePath("SiHunLing");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "WisadelModResources/img/cards/SHL.png";
    private static int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = Wisadel_COLOR;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public SiHunLing() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 3;
        //this.isEthereal = true;消耗
    }


    @Override
    public void upgrade() {
        if (canUpgrade()) {
            this.upgradeName();
            this.upgradeBaseCost(1);
            COST = 1;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // 检查玩家当前已有多少层AncestorPower
        AncestorPower existingPower = (AncestorPower)p.getPower(AncestorPower.POWER_ID);
        int currentAmount = existingPower != null ? existingPower.amount : 0;

        // 计算需要添加的层数，确保总层数不超过3
        int amountToAdd = Math.max(0, 3 - currentAmount);

        // 如果需要添加层数，则添加相应的AncestorPower
        if (amountToAdd > 0) {
            this.addToBot(new ApplyPowerAction(p, p, new AncestorPower(p, amountToAdd), amountToAdd));
        }
    }
}