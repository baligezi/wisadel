package wisadelmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wisadelmod.helpers.ModHelper;
import wisadelmod.power.SFPower;
import static wisadelmod.characters.MyCharacter.PlayerColorEnum.Wisadel_COLOR;


public class SheFu extends CustomCard {
    public static final String ID = ModHelper.makePath("SheFu");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "WisadelModResources/img/cards/Shefu.png";
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = Wisadel_COLOR;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private int Charge = 2;  //充能数

    public SheFu() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = Charge;
    }


    @Override
    public void upgrade() {
        if (canUpgrade()) {
            this.upgradeName();
            Charge = Charge - 1;
            this.magicNumber = this.baseMagicNumber = Charge;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new SFPower(p, Charge), Charge));
    }
}