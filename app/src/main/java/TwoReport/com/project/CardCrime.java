package TwoReport.com.project;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class CardCrime extends CardView {
    private CrimeInfo crime;
    public CardCrime(@NonNull Context context) {
        super(context);
    }
    public CardCrime(@NonNull Context context,CrimeInfo crime) {
        super(context);
        this.crime = crime;
    }

    public CardCrime(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CardCrime(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CrimeInfo getCrime() {
        return crime;
    }

    public void setCrime(CrimeInfo crime) {
        this.crime = crime;
    }
}
