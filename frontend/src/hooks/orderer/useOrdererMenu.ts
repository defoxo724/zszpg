import { useCallback, useEffect, useState } from 'react'
import { ApplicationSettingsApi } from '../../api/ApplicationSettingsApi'
import { CartApi } from '../../api/CartApi'
import { CategoryApi } from '../../api/CategoryApi'
import { MealApi } from '../../api/MealApi'
import type { ApplicationSettings, Cart, Meal, MealCategory } from '../../types'
import { getErrorMessage } from '../../utils/getErrorMessage'

export const useOrdererMenu = () => {
	const [settings, setSettings] = useState<ApplicationSettings | null>(null)
	const [categories, setCategories] = useState<MealCategory[]>([])
	const [meals, setMeals] = useState<Meal[]>([])
	const [cart, setCart] = useState<Cart | null>(null)
	const [loadError, setLoadError] = useState<string | null>(null)
	const [addingMealId, setAddingMealId] = useState<number | null>(null)

	const load = useCallback(async () => {
		try {
			const [loadedSettings, loadedCategories, loadedMeals, loadedCart] = await Promise.all([
				ApplicationSettingsApi.getSettings(),
				CategoryApi.getCategories(),
				MealApi.getMeals(),
				CartApi.getCurrentUserCart(),
			])
			setSettings(loadedSettings)
			setCategories(loadedCategories)
			setMeals(loadedMeals)
			setCart(loadedCart)
			setLoadError(null)
		} catch (error) {
			setLoadError(getErrorMessage(error, 'Nie udało się załadować menu.'))
		}
	}, [])

	useEffect(() => {
		void load()
	}, [load])

	const addMeal = async (mealId: number) => {
		if (!cart) return
		setAddingMealId(mealId)
		try {
			setCart(await CartApi.addMealToCart(cart.id, mealId))
		} finally {
			setAddingMealId(null)
		}
	}

	return { settings, categories, meals, cart, loadError, addingMealId, addMeal }
}
