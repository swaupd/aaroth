const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
    patient_id: {
        type: String,
        required: true,
        unique: true
    },
    name: String,
    age: Number,
    height: Number,
    physical_disabilities: String,
    physical_lifestyle: String,
    icw_kg: Number,
    ecw_kg: Number,
    tbw_kg: Number,
    protein_mass_kg: Number,
    inorganic_salt_kg: Number,
    muscle_mass_kg: Number,
    bone_mass_kg: Number,
    moisture_content_kg: Number,
    skeletal_muscle_mass_kg: Number,
    skeletal_muscle_mass_index: Number,
    segmental_fat_mass_right_hand: Number,
    segmental_fat_mass_left_hand: Number,
    segmental_fat_mass_trunk: Number,
    segmental_fat_mass_right_foot: Number,
    segmental_fat_mass_left_foot: Number,
    segmental_fat_standard_right_hand: Number,
    segmental_fat_standard_left_hand: Number,
    segmental_fat_standard_trunk: Number,
    segmental_fat_standard_right_foot: Number,
    segmental_fat_standard_left_foot: Number,
    recommended_intake_kcal: Number,
    ideal_weight_kg: Number,
    target_weight_kg: Number,
    weight_control_kg: Number,
    muscle_control_kg: Number,
    impedance_slevel_right_hand: Number,
    impedance_slevel_left_hand: Number,
    impedance_slevel_trunk: Number,
    impedance_slevel_right_leg: Number,
    impedance_slevel_left_leg: Number,
}, { timestamps: true });

const User = mongoose.model('User', userSchema);

module.exports = User;
