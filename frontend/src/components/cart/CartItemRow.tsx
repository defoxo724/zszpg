import Button from 'react-bootstrap/Button'
import type { CartItem, Meal } from '../../types'
import { formatPrice } from '../../utils/formatPrice'

interface CartItemRowProps {
	item: CartItem
	meal?: Meal
	onAdd: (mealId: number) => void
	onRemove: (mealId: number) => void
}

const CartItemRow = (props: CartItemRowProps) => (
	<tr>
		<td>{props.meal?.name ?? `Posiłek #${props.item.mealId}`}</td>
		<td>{formatPrice(Number(props.meal?.price ?? 0))}</td>
		<td>{props.item.quantity}</td>
		<td>
			<Button
				className="me-2"
				size="sm"
				variant="outline-secondary"
				onClick={() => props.onRemove(props.item.mealId)}
			>
				-
			</Button>
			<Button
				size="sm"
				variant="outline-primary"
				onClick={() => props.onAdd(props.item.mealId)}
			>
				+
			</Button>
		</td>
	</tr>
)

export default CartItemRow
