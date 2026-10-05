import type { Cart, Meal } from '../../types'

interface OrdererCartPanelProps {
	isOpen: boolean
	cart: Cart | null
	meals: Meal[]
	onClose: () => void
}

const OrdererCartPanel = (props: OrdererCartPanelProps) => {
	const getMealName = (mealId: number) =>
		props.meals.find((meal) => meal.id === mealId)?.name ?? `Posiłek #${mealId}`

	return (
		<>
			<button
				type="button"
				className={`orderer-cart-backdrop ${props.isOpen ? 'is-visible' : ''}`}
				onClick={props.onClose}
				aria-label="Zamknij koszyk"
			/>
			<aside
				className={`orderer-cart-panel ${props.isOpen ? 'is-open' : ''}`}
				aria-hidden={!props.isOpen}
			>
				<header>
					<h2>Koszyk</h2>
					<button type="button" onClick={props.onClose} aria-label="Zamknij koszyk">
						x
					</button>
				</header>
				{!props.cart?.cartItems.length ? (
					<p className="orderer-cart-empty">Koszyk jest pusty.</p>
				) : (
					<ul>
						{props.cart.cartItems.map((item) => (
							<li key={item.id}>
								<span title={getMealName(item.mealId)}>
									{getMealName(item.mealId)}
								</span>
								<strong>× {item.quantity}</strong>
							</li>
						))}
					</ul>
				)}
			</aside>
		</>
	)
}

export default OrdererCartPanel
