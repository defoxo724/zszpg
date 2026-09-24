import { useCallback, useEffect, useState } from 'react'
import { CategoryApi } from '../../api/CategoryApi'
import { MealApi } from '../../api/MealApi'
import type { Meal, MealCategory, MealInput } from '../../types'
import { getErrorMessage } from '../../utils/getErrorMessage'

export const useMeals = () => {
	const [meals, setMeals] = useState<Meal[]>([])
	const [categories, setCategories] = useState<MealCategory[]>([])
	const [loadError, setLoadError] = useState<string | null>(null)

	const load = useCallback(async () => {
		try {
			const [loadedMeals, loadedCategories] = await Promise.all([
				MealApi.getMeals(),
				CategoryApi.getCategories(),
			])
			setMeals(loadedMeals)
			setCategories(loadedCategories)
			setLoadError(null)
		} catch (error) {
			setLoadError(getErrorMessage(error, 'Nie udało się pobrać danych.'))
		}
	}, [])

	useEffect(() => {
		void load()
	}, [load])

	const save = async (mealInput: MealInput, meal?: Meal) => {
		if (meal) {
			await MealApi.updateMeal(meal.id, mealInput)
		} else {
			await MealApi.createMeal(mealInput, mealInput.image ?? undefined)
		}
		await load()
	}

	const remove = async (mealId: number) => {
		await MealApi.deleteMeal(mealId)
		await load()
	}

	return { meals, categories, loadError, save, remove }
}
