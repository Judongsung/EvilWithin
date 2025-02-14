package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;

public class ImprovisedGuard extends AbstractSneckoCard {
    public final static String ID = makeID("ImprovisedGuard");

    public static AbstractCard storage;

    public ImprovisedGuard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        AbstractCard q = SneckoMod.getOffClassCardMatchingPredicate(c -> c.type == CardType.SKILL);
        makeInHand(q);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                addToTop(new MuddleAction(storage));
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }
}