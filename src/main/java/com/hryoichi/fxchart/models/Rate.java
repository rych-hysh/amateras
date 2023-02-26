package com.hryoichi.fxchart.models;

import jakarta.persistence.Entity;

import java.util.Date;


public record Rate(int id, Date date, float open, float close, float low, float high) {
}
