package com.ankush.vitalsigns;

public class SignsValues {
    int
            m_startOfYear,
            m_exercise,
            m_attack,
            m_blueInhaler,
            m_brownInhaler,
            m_outside,
            m_wakeUp,
            m_meal,
            m_feelMeal,
            m_hr,
            m_isAttack;

    double m_rr;

    public SignsValues(){}
    public SignsValues(
            int startOfYear,
            int exercise,
            int attack,
            int blueInhaler,
            int brownInhaler,
            int outside,
            int wakeUp,
            int meal,
            int feelMeal,
            double rr,
            int hr,
            int isAttack
    ) {
        this.m_startOfYear = startOfYear;
        this.m_exercise = exercise;
        this.m_attack = attack;
        this.m_blueInhaler = blueInhaler;
        this.m_brownInhaler = brownInhaler;
        this.m_outside = outside;
        this.m_wakeUp = wakeUp;
        this.m_meal = meal;
        this.m_feelMeal = feelMeal;
        this.m_rr = rr;
        this.m_hr = hr;
        this.m_isAttack = isAttack;
    }

    public SignsValues addTime(int add){
        return new SignsValues(
                this.m_startOfYear + add,
                this.m_exercise+add,
                this.m_attack+add,
                this.m_blueInhaler+add,
                this.m_brownInhaler+add,
                this.m_outside+add,
                this.m_wakeUp+add,
                this.m_meal+add,
                this.m_feelMeal,
                this.m_rr,
                this.m_hr,
                this.m_isAttack
        );
    }
}
