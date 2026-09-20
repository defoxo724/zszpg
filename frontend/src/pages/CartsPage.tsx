import { useCallback, useEffect, useState, type FormEvent } from 'react'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import Stack from 'react-bootstrap/Stack'
import { CartApi } from '../api/CartApi'
import { MealApi } from '../api/MealApi'
import CartItemsTable from '../components/CartItemsTable'
import StatusMessage from '../components/StatusMessage'
import type { Cart, Meal } from '../types'
import { getErrorMessage } from '../utils/getErrorMessage'

const CartsPage = () => {
	const [cart, setCart] = useState<Cart | null>(null)
	const [meals, setMeals] = useState<Meal[]>([])
	const [selectedMealId, setSelectedMealId] = useState(0)
	const [error, setError] = useState<string | null>(null)

	const loadCart = useCallback(async () => {
		try {
			const [loadedCart, loadedMeals] = await Promise.all([
				CartApi.getCurrentUserCart(),
				MealApi.getMeals(),
			])
			setCart(loadedCart)
			setMeals(loadedMeals)
			setSelectedMealId(loadedMeals[0]?.id ?? 0)
			setError(null)
		} catch (error) {
			setError(getErrorMessage(error, 'Nie udało się pobrać koszyka.'))
		}
	}, [])

	useEffect(() => {
		void loadCart()
	}, [loadCart])

	const handleAddMeal = async (mealId: number) => {
		if (!cart) return

		try {
			setCart(await CartApi.addMealToCart(cart.id, mealId))
			setError(null)
		} catch (error) {
			setError(getErrorMessage(error, 'Nie udało się dodać posiłku.'))
		}
	}

	const handleRemoveMeal = async (mealId: number) => {
		if (!cart) return

		try {
			setCart(await CartApi.removeMealFromCart(cart.id, mealId))
			setError(null)
		} catch (error) {
			setError(getErrorMessage(error, 'Nie udało się usunąć posiłku.'))
		}
	}

	const handleAddSelectedMeal = (event: FormEvent) => {
		event.preventDefault()
		if (selectedMealId) void handleAddMeal(selectedMealId)
	}

	return (
		<>
			<h1 className="mb-4">Twój koszyk</h1>
			<StatusMessage message={error} />
			{cart && (
				<>
					<Form onSubmit={handleAddSelectedMeal} className="mb-4">
						<Stack direction="horizontal" gap={2}>
							<Form.Select
								value={selectedMealId}
								onChange={(event) => setSelectedMealId(Number(event.target.value))}
							>
								{meals.map((meal) => (
									<option key={meal.id} value={meal.id}>
										{meal.name} — {Number(meal.price).toFixed(2)} zł
									</option>
								))}
							</Form.Select>
							<Button type="submit" disabled={!meals.length}>
								Dodaj do koszyka
							</Button>
						</Stack>
					</Form>
					<CartItemsTable
						items={cart.cartItems}
						meals={meals}
						onAdd={handleAddMeal}
						onRemove={handleRemoveMeal}
					/>
				</>
			)}
		</>
	)
}
export default CartsPage
