import Table from 'react-bootstrap/Table'
import type { CartItem, Meal } from '../../types'
import CartItemRow from './CartItemRow'
import { formatPrice } from '../../utils/formatPrice'

interface CartItemsTableProps {
	items: CartItem[]
	meals: Meal[]
	onAdd: (mealId: number) => void
	onRemove: (mealId: number) => void
}

const CartItemsTable = (props: CartItemsTableProps) => {
	const findMeal = (mealId: number) => props.meals.find((meal) => meal.id === mealId)
	const totalPrice = props.items.reduce(
		(sum, item) => sum + Number(findMeal(item.mealId)?.price ?? 0) * item.quantity,
		0,
	)

	return (
		<>
			{props.items.length === 0 && <p>Twój koszyk jest pusty.</p>}
			<Table responsive>
				<thead>
					<tr>
						<th>Posiłek</th>
						<th>Cena</th>
						<th>Ilość</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					{props.items.map((item) => (
						<CartItemRow
							key={item.id}
							item={item}
							meal={findMeal(item.mealId)}
							onAdd={props.onAdd}
							onRemove={props.onRemove}
						/>
					))}
				</tbody>
			</Table>
			<p className="fw-bold text-end">Razem: {formatPrice(totalPrice)}</p>
		</>
	)
}

export default CartItemsTable
