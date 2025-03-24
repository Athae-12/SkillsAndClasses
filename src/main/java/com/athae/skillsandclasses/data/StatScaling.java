package com.athae.skillsandclasses.data;

public enum StatScaling {
    NONE {
        @Override
        public float scale(float val, float lvl) {
            return val;
        }
    },

    NORMAL {
        @Override
        public float scale(float val, float lvl) {
            return val * GameBalanceConfig.get().NORMAL_STAT_SCALING.getMultiFor(lvl);
        }
    },
    CORE {
        @Override
        public float scale(float val, float lvl) {
            return val * GameBalanceConfig.get().CORE_STAT_SCALING.getMultiFor(lvl);
        }
    },
    STAT_REQ {
        @Override
        public float scale(float val, float lvl) {
            return val * GameBalanceConfig.get().STAT_REQ_SCALING.getMultiFor(lvl);
        }
    },
    MOB_DAMAGE {
        @Override
        public float scale(float val, float lvl) {
            return val * GameBalanceConfig.get().MOB_DAMAGE_SCALING.getMultiFor(lvl);
        }
    },
    SLOW {
        @Override
        public float scale(float val, float lvl) {
            return val * GameBalanceConfig.get().SLOW_STAT_SCALING.getMultiFor(lvl);
        }
    };

    StatScaling() {
    }

    public abstract float scale(float val, float lvl);
}
