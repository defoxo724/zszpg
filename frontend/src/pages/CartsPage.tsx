import { useCallback, useEffect, useState, type FormEvent } from 'react'
import { Link } from 'react-router-dom'
import { CartApi } from '../api/CartApi'
import StatusMessage from '../components/StatusMessage'
import { useAuth } from '../context/AuthContext'
import type { Cart } from '../types'
import { getErrorMessage } from '../utils/getErrorMessage'
import { getSavedCartIds, removeSavedCartId, saveCartId } from '../utils/cartStorage'

const loadSavedCarts = async () => {
	const cartRequests = getSavedCartIds().map(async (cartId) => {
		try {
			return await CartApi.getCart(cartId)
		} catch {
			return null
		}
	})

	const savedCarts = await Promise.all(cartRequests)
	return savedCarts.filter((cart): cart is Cart => cart !== null)
}

const CartsPage = () => {
	const { user } = useAuth()
	const [carts, setCarts] = useState<Cart[]>([])
	const [comment, setComment] = useState('')
	const [error, setError] = useState<string | null>(null)
	const loadCarts = useCallback(async () => {
		try {
			const loadedCarts =
				user?.type === 'ADMIN' ? await CartApi.getCarts() : await loadSavedCarts()
			setCarts(loadedCarts)
			setError(null)
		} catch (error) {
			setError(getErrorMessage(error, 'Nie udało się pobrać koszyków.'))
		}
	}, [user])

	useEffect(() => {
		void loadCarts()
	}, [loadCarts])

	const handleCreateCart = async (event: FormEvent) => {
		event.preventDefault()

		try {
			const createdCart = await CartApi.createCart(comment)
			saveCartId(createdCart.id)
			setComment('')
			await loadCarts()
		} catch (error) {
			setError(getErrorMessage(error, 'Nie udało się utworzyć koszyka.'))
		}
	}

	const handleDeleteCart = async (cartId: number) => {
		const deletionConfirmed = confirm('Usunąć koszyk?')
		if (!deletionConfirmed) {
			return
		}

		try {
			await CartApi.deleteCart(cartId)
			removeSavedCartId(cartId)
			await loadCarts()
		} catch (error) {
			setError(getErrorMessage(error, 'Nie udało się usunąć koszyka.'))
		}
	}

	return (
		<>
			<h1 className="mb-4">Koszyki</h1>
			<StatusMessage message={error} />
			<form className="card card-body mb-4" onSubmit={handleCreateCart}>
				<h2 className="h5">Nowy koszyk</h2>
				<div className="d-flex gap-2">
					<input
						className="form-control"
						placeholder="Komentarz (opcjonalny)"
						value={comment}
						onChange={(event) => setComment(event.target.value)}
					/>
					<button className="btn btn-primary">Utwórz</button>
				</div>
			</form>
			<div className="row g-3">
				{carts.map((cart) => (
					<div className="col-md-6" key={cart.id}>
						<div className="card">
							<div className="card-body">
								<h2 className="h5">Koszyk #{cart.id}</h2>
								<p>{cart.comment || 'Bez komentarza'}</p>
								<p>Pozycji: {cart.cartItems.length}</p>
								<Link className="btn btn-primary me-2" to={`/carts/${cart.id}`}>
									Otwórz
								</Link>
								<button
									className="btn btn-outline-danger"
									onClick={() => handleDeleteCart(cart.id)}
								>
									Usuń
								</button>
							</div>
						</div>
					</div>
				))}
			</div>
			{!carts.length && <p>Brak koszyków.</p>}
		</>
	)
}
export default CartsPage
