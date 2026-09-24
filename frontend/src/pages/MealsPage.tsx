import Button from 'react-bootstrap/Button'
import StatusMessage from '../components/common/StatusMessage'
import PageHeader from '../components/common/PageHeader'
import MealsTable from '../components/meal/MealsTable'
import { useAuth } from '../context/AuthContext'
import { showMealFormDialog } from '../dialogs/mealFormDialog'
import { useMeals } from '../hooks/meal/useMeals'
import type { Meal } from '../types'
import { getErrorMessage } from '../utils/getErrorMessage'
import { confirmAction, showError, showSuccess } from '../utils/sweetAlert'

const MealsPage = () => {
	const { user } = useAuth()
	const mealsState = useMeals()
	const isAdmin = user?.type === 'ADMIN'

	const handleOpenForm = async (meal?: Meal) => {
		const mealInput = await showMealFormDialog(mealsState.categories, meal)
		if (!mealInput) return

		try {
			await mealsState.save(mealInput, meal)
			void showSuccess(meal ? 'Posiłek został zaktualizowany' : 'Posiłek został dodany')
		} catch (error) {
			await showError('Błąd zapisu', getErrorMessage(error, 'Nie udało się zapisać posiłku.'))
		}
	}

	const handleDelete = async (mealId: number) => {
		if (!(await confirmAction('Usunąć posiłek?', 'Tej operacji nie można cofnąć.'))) return

		try {
			await mealsState.remove(mealId)
			void showSuccess('Posiłek został usunięty')
		} catch (error) {
			await showError(
				'Błąd usuwania',
				getErrorMessage(error, 'Nie udało się usunąć posiłku.'),
			)
		}
	}

	return (
		<>
			<PageHeader title="Posiłki">
				{isAdmin && (
					<Button
						size="sm"
						onClick={() => void handleOpenForm()}
						disabled={!mealsState.categories.length}
					>
						Dodaj posiłek
					</Button>
				)}
			</PageHeader>
			<StatusMessage message={mealsState.loadError} />
			<MealsTable
				meals={mealsState.meals}
				categories={mealsState.categories}
				isAdmin={isAdmin}
				onEdit={(meal) => void handleOpenForm(meal)}
				onDelete={handleDelete}
			/>
		</>
	)
}

export default MealsPage
