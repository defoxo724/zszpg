import CartContent from '../components/cart/CartContent'
import StatusMessage from '../components/common/StatusMessage'
import { useCart } from '../hooks/cart/useCart'

const CartsPage = () => {
	const cartState = useCart()

	return (
		<>
			<h1 className="mb-4">Twój koszyk</h1>
			<StatusMessage message={cartState.loadError} />
			{cartState.cart && (
				<CartContent
					cart={cartState.cart}
					meals={cartState.meals}
					buttonLabel="Dodaj do koszyka"
					onAdd={cartState.addMeal}
					onRemove={cartState.removeMeal}
				/>
			)}
		</>
	)
}

export default CartsPage
