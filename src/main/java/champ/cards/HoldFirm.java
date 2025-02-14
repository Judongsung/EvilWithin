package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

import static champ.ChampMod.loadJokeCardImage;

public class HoldFirm extends AbstractChampCard {

    public final static String ID = makeID("HoldFirm");

    //stupid intellij stuff skill, self, rare

    private static final int BLOCK = 12;
    private static final int UPG_BLOCK = 5;

    public HoldFirm() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = 12;
       // tags.add(ChampMod.TECHNIQUE);
        postInit();
        loadJokeCardImage(this, "HoldFirm.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
       // techique();
        blck();
        applyToSelf(new CounterPower(magicNumber));
        applyToSelf(new BlurPower(p, 1));
    }

    public void upp() {
        upgradeMagicNumber(4);
        upgradeBlock(UPG_BLOCK);
    }
}