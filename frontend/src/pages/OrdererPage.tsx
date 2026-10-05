import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import OrdererCartButton from '../components/orderer/OrdererCartButton'
import OrdererCartPanel from '../components/orderer/OrdererCartPanel'
import OrdererHeroSection from '../components/orderer/OrdererHeroSection'
import OrdererMenuSection from '../components/orderer/OrdererMenuSection'
import { useAuth } from '../context/AuthContext'
import { useOrdererMenu } from '../hooks/orderer/useOrdererMenu'
import { getErrorMessage } from '../utils/getErrorMessage'
import { confirmAction, showError, showSuccess } from '../utils/sweetAlert'
import './orderer.css'

const OrdererPage = () => {
	const ordererState = useOrdererMenu()
	const { signOut } = useAuth()
	const navigate = useNavigate()
	const [activeCategoryId, setActiveCategoryId] = useState<number | null>(null)
	const [isCartOpen, setIsCartOpen] = useState(false)

	useEffect(() => {
		if (activeCategoryId === null && ordererState.categories.length) {
			setActiveCategoryId(ordererState.categories[0].id)
		}
	}, [activeCategoryId, ordererState.categories])

	const handleAddMeal = async (mealId: number) => {
		try {
			await ordererState.addMeal(mealId)
			void showSuccess('Posiłek został dodany do koszyka')
		} catch (error) {
			await showError(
				'Błąd koszyka',
				getErrorMessage(error, 'Nie udało się dodać posiłku do koszyka.'),
			)
		}
	}

	const handleSignOut = async () => {
		if (!(await confirmAction('Wylogować się?', 'Bieżąca sesja zostanie zakończona.'))) return
		try {
			await signOut()
			navigate('/login')
		} catch {
			await showError('Błąd wylogowania', 'Nie udało się zakończyć sesji.')
		}
	}

	const itemCount =
		ordererState.cart?.cartItems.reduce((sum, item) => sum + item.quantity, 0) ?? 0

	return (
		<main className="orderer-page">
			<button type="button" className="orderer-sign-out" onClick={() => void handleSignOut()}>
				Wyloguj
			</button>
			<OrdererCartButton itemCount={itemCount} onClick={() => setIsCartOpen(true)} />
			<OrdererCartPanel
				isOpen={isCartOpen}
				cart={ordererState.cart}
				meals={ordererState.meals}
				onClose={() => setIsCartOpen(false)}
			/>
			{ordererState.loadError && (
				<div className="orderer-load-error">{ordererState.loadError}</div>
			)}
			<OrdererHeroSection
				settings={ordererState.settings}
				onShowMenu={() =>
					document.getElementById('menu')?.scrollIntoView({ behavior: 'smooth' })
				}
			/>
			<OrdererMenuSection
				categories={ordererState.categories}
				meals={ordererState.meals}
				activeCategoryId={activeCategoryId}
				addingMealId={ordererState.addingMealId}
				onCategorySelect={setActiveCategoryId}
				onMealAdd={(mealId) => void handleAddMeal(mealId)}
			/>
		</main>
	)
}

export default OrdererPage
