package org.example.cli;

import org.example.model.PriceZone;

// Will parse zone/cvs/date
public class Args {
    public static PriceZone parseZone(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            if ("--zone".equals(args[i])) {
                return PriceZone.valueOf(args[i + 1].toUpperCase());
            }
        }
        return PriceZone.SE3;
    }
}
