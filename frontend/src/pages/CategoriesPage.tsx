import Button from 'react-bootstrap/Button'
import MealCategoryList from '../components/category/MealCategoryList'
import PageHeader from '../components/common/PageHeader'
import StatusMessage from '../components/common/StatusMessage'
import { useAuth } from '../context/AuthContext'
import { showCategoryFormDialog } from '../dialogs/categoryFormDialog'
import { useMealCategories } from '../hooks/category/useMealCategories'
import type { MealCategory } from '../types'
import { getErrorMessage } from '../utils/getErrorMessage'
import { confirmAction, showError, showSuccess } from '../utils/sweetAlert'

const CategoriesPage = () => {
	const { user } = useAuth()
	const categoriesState = useMealCategories()
	const isAdmin = user?.type === 'ADMIN'

	const handleOpenForm = async (category?: MealCategory) => {
		const name = await showCategoryFormDialog(category)
		if (!name) return

		try {
			await categoriesState.save(name, category)
			void showSuccess(
				category ? 'Kategoria została zaktualizowana' : 'Kategoria została dodana',
			)
		} catch (error) {
			await showError(
				'Błąd zapisu',
				getErrorMessage(error, 'Nie udało się zapisać kategorii.'),
			)
		}
	}

	const handleDelete = async (categoryId: number) => {
		if (!(await confirmAction('Usunąć kategorię?', 'Tej operacji nie można cofnąć.'))) return

		try {
			await categoriesState.remove(categoryId)
			void showSuccess('Kategoria została usunięta')
		} catch (error) {
			await showError(
				'Błąd usuwania',
				getErrorMessage(error, 'Nie udało się usunąć kategorii.'),
			)
		}
	}

	return (
		<>
			<PageHeader title="Kategorie posiłków">
				{isAdmin && (
					<Button size="sm" onClick={() => void handleOpenForm()}>
						Dodaj kategorię
					</Button>
				)}
			</PageHeader>
			<StatusMessage message={categoriesState.loadError} />
			<MealCategoryList
				categories={categoriesState.categories}
				isAdmin={isAdmin}
				onEdit={(category) => void handleOpenForm(category)}
				onDelete={handleDelete}
			/>
		</>
	)
}

export default CategoriesPage
