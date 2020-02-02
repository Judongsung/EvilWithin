
package sneckomod.cards.unknowns;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.CardIgnore;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.function.Predicate;

@CardIgnore
public class UnknownClass extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownClass");

    public UnknownClass(CardColor cardColor) {
        super(ID + cardColor.name(), determineCardImg(cardColor), CardType.SKILL, CardRarity.UNCOMMON);
        myColor = cardColor;
        name = "???";
        originalName = "???";
        if (CardCrawlGame.isInARun())
            rawDescription = "sneckomod:Unknown " + getCharName(myColor) + " Card.";
        initializeDescription();
    }

    public static String determineCardImg(CardColor myColor) {
        switch (myColor.name()) {
            case "RED":
                return "UnknownIronclad";
            case "BLUE":
                return "UnknownDefect";
            case "GREEN":
                return "UnknownSilent";
            case "PURPLE":
                return "UnknownWatcher";
            case "GUARDIAN":
                return "UnknownGuardian";
            case "SLIMEBOUND":
                return "UnknownSlimeBoss";
            case "HEXA_GHOST_PURPLE":
                return "UnknownHexaghost";
            default:
                return "UnknownModded";
        }
    }

    public static String getCharName(CardColor myColor) {
        ArrayList<AbstractPlayer> theDudes = new ArrayList<AbstractPlayer>(CardCrawlGame.characterManager.getAllCharacters());
        for (AbstractPlayer p : theDudes) {
            if (p.getCardColor() == myColor)
                return p.getLocalizedCharacterName().replace("The ", "");
        }
        return "You should never see this. Report to Vex";
    }


    private CardColor myColor;

    @Override
    public AbstractCard makeCopy() {
        return new UnknownClass(myColor);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.color == myColor;
    }
}

