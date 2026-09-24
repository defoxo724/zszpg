import { Link, useParams } from 'react-router-dom'
import CartContent from '../components/cart/CartContent'
import StatusMessage from '../components/common/StatusMessage'
import { useCart } from '../hooks/cart/useCart'

const CartDetailsPage = () => {
	const cartId = Number(useParams().cartId)
	const cartState = useCart(cartId)

	return (
		<>
			<Link to="/carts">←Wróć do koszyków</Link>
			<h1 className="my-4">Koszyk #{cartId}</h1>
			<StatusMessage message={cartState.loadError} />
			{cartState.cart && (
				<>
					<p>
						<strong>Komentarz:</strong> {cartState.cart.comment || 'brak'}
					</p>
					<CartContent
						cart={cartState.cart}
						meals={cartState.meals}
						buttonLabel="Dodaj posiłek"
						onAdd={cartState.addMeal}
						onRemove={cartState.removeMeal}
					/>
				</>
			)}
		</>
	)
}

export default CartDetailsPage
