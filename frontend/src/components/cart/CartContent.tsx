import { useState } from 'react'
import type { Cart, Meal } from '../../types'
import { getErrorMessage } from '../../utils/getErrorMessage'
import { showError, showSuccess } from '../../utils/sweetAlert'
import CartItemsTable from './CartItemsTable'
import MealSelectorForm from './MealSelectorForm'

interface CartContentProps {
	cart: Cart
	meals: Meal[]
	buttonLabel: string
	onAdd: (mealId: number) => Promise<void>
	onRemove: (mealId: number) => Promise<void>
}

const CartContent = (props: CartContentProps) => {
	const [selectedMealId, setSelectedMealId] = useState(props.meals[0]?.id ?? 0)

	const handleAdd = async (mealId: number) => {
		try {
			await props.onAdd(mealId)
			void showSuccess('Posiłek został dodany do koszyka')
		} catch (error) {
			await showError('Błąd koszyka', getErrorMessage(error, 'Nie udało się dodać posiłku.'))
		}
	}

	const handleRemove = async (mealId: number) => {
		try {
			await props.onRemove(mealId)
			void showSuccess('Zmieniono ilość posiłku')
		} catch (error) {
			await showError('Błąd koszyka', getErrorMessage(error, 'Nie udało się usunąć posiłku.'))
		}
	}

	return (
		<>
			<MealSelectorForm
				meals={props.meals}
				selectedMealId={selectedMealId}
				buttonLabel={props.buttonLabel}
				onMealSelect={setSelectedMealId}
				onSubmit={handleAdd}
			/>
			<CartItemsTable
				items={props.cart.cartItems}
				meals={props.meals}
				onAdd={handleAdd}
				onRemove={handleRemove}
			/>
		</>
	)
}

export default CartContent
