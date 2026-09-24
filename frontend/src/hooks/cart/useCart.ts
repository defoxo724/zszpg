import { useCallback, useEffect, useState } from 'react'
import { CartApi } from '../../api/CartApi'
import { MealApi } from '../../api/MealApi'
import type { Cart, Meal } from '../../types'
import { getErrorMessage } from '../../utils/getErrorMessage'

export const useCart = (cartId?: number) => {
	const [cart, setCart] = useState<Cart | null>(null)
	const [meals, setMeals] = useState<Meal[]>([])
	const [loadError, setLoadError] = useState<string | null>(null)

	const load = useCallback(async () => {
		if (cartId !== undefined && !Number.isInteger(cartId)) {
			setLoadError('Nieprawidłowy identyfikator koszyka.')
			return
		}

		try {
			const cartRequest =
				cartId === undefined ? CartApi.getCurrentUserCart() : CartApi.getCart(cartId)
			const [loadedCart, loadedMeals] = await Promise.all([cartRequest, MealApi.getMeals()])
			setCart(loadedCart)
			setMeals(loadedMeals)
			setLoadError(null)
		} catch (error) {
			setLoadError(getErrorMessage(error, 'Nie udało się pobrać koszyka.'))
		}
	}, [cartId])

	useEffect(() => {
		void load()
	}, [load])

	const addMeal = async (mealId: number) => {
		if (!cart) return
		setCart(await CartApi.addMealToCart(cart.id, mealId))
	}

	const removeMeal = async (mealId: number) => {
		if (!cart) return
		setCart(await CartApi.removeMealFromCart(cart.id, mealId))
	}

	return { cart, meals, loadError, addMeal, removeMeal }
}
