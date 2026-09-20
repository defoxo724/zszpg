import type { CartItem, Meal } from '../types'

interface CartItemsTableProps {
	items: CartItem[]
	meals: Meal[]
	onAdd: (mealId: number) => void
	onRemove: (mealId: number) => void
}

const CartItemsTable = ({ items, meals, onAdd, onRemove }: CartItemsTableProps) => {
	const findMeal = (mealId: number) => meals.find((meal) => meal.id === mealId)
	const totalPrice = items.reduce(
		(sum, item) => sum + Number(findMeal(item.mealId)?.price ?? 0) * item.quantity,
		0,
	)

	return (
		<>
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
					{items.map((item) => {
						const meal = findMeal(item.mealId)
						return (
							<tr key={item.id}>
								<td>{meal?.name ?? `Posiłek #${item.mealId}`}</td>
								<td>{Number(meal?.price ?? 0).toFixed(2)} zł</td>
								<td>{item.quantity}</td>
								<td>
									<Button
										className="me-2"
										size="sm"
										variant="outline-secondary"
										onClick={() => onRemove(item.mealId)}
									>
										-
									</Button>
									<Button
										size="sm"
										variant="outline-primary"
										onClick={() => onAdd(item.mealId)}
									>
										+
									</Button>
								</td>
							</tr>
						)
					})}
				</tbody>
			</Table>
			<p className="fw-bold text-end">Razem: {totalPrice.toFixed(2)} zł</p>
		</>
	)
}
export default CartItemsTable
import Button from 'react-bootstrap/Button'
import Table from 'react-bootstrap/Table'
