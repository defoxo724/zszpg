import { useCallback, useEffect, useState } from 'react'
import Table from 'react-bootstrap/Table'
import { CartApi } from '../api/CartApi'
import StatusMessage from '../components/StatusMessage'
import { useAuth } from '../context/AuthContext'
import type { Cart } from '../types'
import { getErrorMessage } from '../utils/getErrorMessage'

const CartsPage = () => {
	const { user } = useAuth()
	const [carts, setCarts] = useState<Cart[]>([])
	const [error, setError] = useState<string | null>(null)
	const loadCarts = useCallback(async () => {
		try {
			const loadedCarts =
				user?.type === 'ADMIN'
					? await CartApi.getCarts()
					: [await CartApi.getCurrentUserCart()]
			setCarts(loadedCarts)
			setError(null)
		} catch (error) {
			setError(getErrorMessage(error, 'Nie udało się pobrać koszyków.'))
		}
	}, [user])

	useEffect(() => {
		void loadCarts()
	}, [loadCarts])

	return (
		<>
			<h1 className="mb-4">Koszyki</h1>
			<StatusMessage message={error} />
			{carts.length > 0 && (
				<Table responsive striped className="align-middle">
					<thead>
						<tr>
							<th>Numer</th>
							<th>Komentarz</th>
							<th>Liczba pozycji</th>
						</tr>
					</thead>
					<tbody>
						{carts.map((cart) => (
							<tr key={cart.id}>
								<td>#{cart.id}</td>
								<td>{cart.comment || 'Bez komentarza'}</td>
								<td>{cart.cartItems.length}</td>
							</tr>
						))}
					</tbody>
				</Table>
			)}
			{!carts.length && <p>Brak koszyków.</p>}
		</>
	)
}
export default CartsPage
