package timeeater.suspend;

import automaton.cards.AbstractBronzeCard;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import timeeater.powers.OnRetrieveCardPower;
import timeeater.powers.OnSuspendCardPower;
import timeeater.powers.RetrieveLessNextTurnPower;

import static automaton.FunctionHelper.*;
import static timeeater.util.Wiz.atb;

public class SuspendHelper {
    public static CardGroup suspendGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    public static final int SUSPEND_DRAW_PER_TURN = 3;

    public static final Vector2[] cardPositions = {
            new Vector2(218f * Settings.xScale, HEIGHT_SEQUENCE),
            new Vector2(293f * Settings.xScale, HEIGHT_SEQUENCE),
            new Vector2(368f * Settings.xScale, HEIGHT_SEQUENCE)
    };

    public static void atCombatStart() {
        suspendGroup.clear();
    }

    public static void atStartOfTurnPostDraw() {
        int suspendDrawAmt = SUSPEND_DRAW_PER_TURN;
        if (AbstractDungeon.player.hasPower(RetrieveLessNextTurnPower.ID)) {
            suspendDrawAmt -= AbstractDungeon.player.getPower(RetrieveLessNextTurnPower.ID).amount;
        }
        suspendDrawAmt = Math.min(suspendDrawAmt, suspendGroup.size());
        for (int i = 0; i < suspendDrawAmt; i++) {
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractCard q = suspendGroup.getTopCard();
                    AbstractDungeon.player.drawPile.addToTop(q);
                    suspendGroup.removeCard(q);
                    AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
                        @Override
                        public void update() {
                            isDone = true;
                            for (AbstractPower q2 : AbstractDungeon.player.powers) {
                                if (q2 instanceof OnRetrieveCardPower) {
                                    ((OnRetrieveCardPower) q2).receiveRetrieve(q);
                                }
                            }
                        }
                    });
                    AbstractDungeon.actionManager.addToTop(new DrawCardAction(1));
                }
            });
        }
    }

    public static void suspend(AbstractCard q) {
        suspendGroup.addToBottom(q);
        q.current_x = -150 * Settings.xScale;
        q.current_y = HEIGHT_FUNCTION;
        q.targetDrawScale = SEQUENCED_CARD_SIZE;
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnSuspendCardPower) {
                ((OnSuspendCardPower) p).receiveSuspended(q);
            }
        }
    }

    public static void update() {
        for (int i = 0; i < suspendGroup.size(); i++) {
            AbstractCard c = suspendGroup.group.get(i);
            c.target_x = cardPositions[i].x;
            c.target_y = cardPositions[i].y;
            c.update();
            c.updateHoverLogic();
            if (c.hb.hovered) {
                c.targetDrawScale = AbstractBronzeCard.functionPreviewCardScale;
            } else {
                c.targetDrawScale = SEQUENCED_CARD_SIZE;
            }
        }
    }

    public static void render(SpriteBatch sb) {
        for (AbstractCard q : suspendGroup.group) {
            q.render(sb);
        }
    }
}
