import { useCallback, useEffect, useState, type FormEvent } from 'react'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import Stack from 'react-bootstrap/Stack'
import { Link, useParams } from 'react-router-dom'
import { CartApi } from '../api/CartApi'
import { MealApi } from '../api/MealApi'
import CartItemsTable from '../components/CartItemsTable'
import StatusMessage from '../components/StatusMessage'
import type { Cart, Meal } from '../types'
import { getErrorMessage } from '../utils/getErrorMessage'

const CartDetailsPage = () => {
	const cartId = Number(useParams().cartId)
	const [cart, setCart] = useState<Cart | null>(null)
	const [meals, setMeals] = useState<Meal[]>([])
	const [selectedMealId, setSelectedMealId] = useState(0)
	const [error, setError] = useState<string | null>(null)

	const loadCartDetails = useCallback(async () => {
		if (!Number.isInteger(cartId)) {
			setError('Nieprawidłowy identyfikator koszyka.')
			return
		}
		try {
			const [loadedCart, loadedMeals] = await Promise.all([
				CartApi.getCart(cartId),
				MealApi.getMeals(),
			])

			setCart(loadedCart)
			setMeals(loadedMeals)
			setSelectedMealId((currentMealId) => currentMealId || loadedMeals[0]?.id || 0)
			setError(null)
		} catch (error) {
			setError(getErrorMessage(error, 'Nie udało się pobrać koszyka.'))
		}
	}, [cartId])

	useEffect(() => {
		void loadCartDetails()
	}, [loadCartDetails])

	const handleAddMeal = async (mealId: number) => {
		try {
			const updatedCart = await CartApi.addMealToCart(cartId, mealId)
			setCart(updatedCart)
		} catch (error) {
			setError(getErrorMessage(error, 'Nie udało się dodać posiłku.'))
		}
	}

	const handleRemoveMeal = async (mealId: number) => {
		try {
			const updatedCart = await CartApi.removeMealFromCart(cartId, mealId)
			setCart(updatedCart)
		} catch (error) {
			setError(getErrorMessage(error, 'Nie udało się usunąć posiłku.'))
		}
	}

	const handleAddSelectedMeal = (event: FormEvent) => {
		event.preventDefault()

		if (selectedMealId) {
			void handleAddMeal(selectedMealId)
		}
	}

	return (
		<>
			<Link to="/carts">←Wróć do koszyków</Link>
			<h1 className="my-4">Koszyk #{cartId}</h1>
			<StatusMessage message={error} />
			{cart && (
				<>
					<p>
						<strong>Komentarz:</strong> {cart.comment || 'brak'}
					</p>
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
								Dodaj posiłek
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
export default CartDetailsPage
