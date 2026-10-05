import type { Meal } from '../../types'
import { formatPrice } from '../../utils/formatPrice'

interface OrdererMealCardProps {
	meal: Meal
	isAdding: boolean
	onAdd: (mealId: number) => void
}

const OrdererMealCard = (props: OrdererMealCardProps) => (
	<article className="orderer-meal-card">
		{props.meal.imageUrl ? (
			<img src={props.meal.imageUrl} alt={props.meal.name} />
		) : (
			<div className="orderer-meal-image-placeholder">Brak zdjęcia</div>
		)}
		<div className="orderer-meal-content">
			<h3 title={props.meal.name}>{props.meal.name}</h3>
			<p title={props.meal.description}>{props.meal.description}</p>
			<footer>
				<strong>{formatPrice(Number(props.meal.price))}</strong>
				<button
					type="button"
					onClick={() => props.onAdd(props.meal.id)}
					disabled={props.isAdding}
				>
					{props.isAdding ? 'Dodawanie…' : 'Dodaj do koszyka'}
				</button>
			</footer>
		</div>
	</article>
)

export default OrdererMealCard
