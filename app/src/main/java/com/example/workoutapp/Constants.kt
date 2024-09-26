package com.example.workoutapp

object Constants {
    fun defaultExerciseIst():ArrayList<ExerciseModels>{
        val exerciseList = ArrayList<ExerciseModels>()
        val armRotation = ExerciseModels(
            1,"Arm Rotation",
            R.drawable.arm_rotation_image,
            false,
            false)

        val pullUps =  ExerciseModels(
            2,"Pull-ups",
            R.drawable.pull_ups_image,
            false,
            false)

        val PushUps = ExerciseModels(
            3,"Push-ups",
            R.drawable.push_ups_image,
            false,
            false)


        val sit_up = ExerciseModels(
            4,"Sit-ups",
            R.drawable.sit_up_images,
            false,
            false)

        val squats = ExerciseModels(
            5,"squats",
            R.drawable.leg_squats_image,
            false,
            false)

        exerciseList.add(armRotation)
        exerciseList.add(pullUps)
        exerciseList.add(PushUps)
        exerciseList.add(sit_up)
        exerciseList.add(squats)


        return exerciseList
    }

}